package org.pyjjs.scheduler.core.api.impl

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.UntypedActor
import akka.japi.Creator
import com.google.common.base.Preconditions.checkNotNull
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import com.typesafe.config.ConfigFactory
import org.pyjjs.scheduler.core.api.PlanMergingController
import org.pyjjs.scheduler.core.api.PlanRepresentative
import org.pyjjs.scheduler.core.api.Scheduler
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActor
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.ResourceSupervisor
import org.pyjjs.scheduler.core.api.impl.actors.system.ModificationController
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingController
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.EntityCreatedMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.EntityRemovedMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.EntityUpdatedMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActor
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.TaskSupervisor
import org.pyjjs.scheduler.core.data.HashSetDataSourceImpl
import org.pyjjs.scheduler.core.data.ObservableDataSource
import org.pyjjs.scheduler.core.model.IdentifiableObject
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import org.slf4j.LoggerFactory
import java.util.*

class SchedulerImpl @JvmOverloads constructor(override val dataSource: ObservableDataSource = HashSetDataSourceImpl()) : Scheduler {

    private val uuid = UUID.randomUUID()

    private lateinit var actorSystem: ActorSystem

    private val agentToEntityMap = HashMap<IdentifiableObject, ActorRef>()

    private val agentToEntityTypeMap = HashBiMap.create<Class<out IdentifiableObject>, Class<out UntypedActor>>()

    @Volatile override var isRunning = false

    private var schedulingController: ActorRef? = null

    private var modificationController: ActorRef? = null

    private var taskSupervisor: ActorRef? = null

    private var resourceSupervisor: ActorRef? = null

    private val mergingController: PlanMergingController

    private val LOG = LoggerFactory.getLogger(String.format("Scheduler [%s]", uuid.toString()))

    init {
        mergingController = PlanMergingController()
        prepareAndLaunch()
    }

    private fun prepareAndLaunch() {
        fillAgentToEntityClassMapping()
        val config = ConfigFactory.load("default_scheduler_config.properties")
        actorSystem = ActorSystem.apply("scheduler", config)
    }

    private fun createDataSourceModificationControllerListener() {
        val interests = Arrays.asList(Task::class.java, Resource::class.java)
        this.dataSource.addDataSourceListener(interests, object : ObservableDataSource.DataSourceListener {

            override fun onCreate(entity: IdentifiableObject) {
                val msg = EntityCreatedMessage(entity)
                modificationController!!.tell(msg, ActorRef.noSender())
            }

            override fun onUpdate(entity: IdentifiableObject) {
                val msg = EntityUpdatedMessage(entity)
                modificationController!!.tell(msg, ActorRef.noSender())
            }

            override fun onRemove(entity: IdentifiableObject) {
                val msg = EntityRemovedMessage(entity)
                modificationController!!.tell(msg, ActorRef.noSender())
            }
        })
    }

    private fun createSystemAgents() {
        taskSupervisor = actorSystem.actorOf(Props.create(TaskSupervisor::class.java), "tasks")
        resourceSupervisor = actorSystem.actorOf(Props.create(ResourceSupervisor::class.java), "resources")

        schedulingController = actorSystem.actorOf(Props.create(SchedulingController::class.java, mergingController))
        actorSystem.eventStream().subscribe(schedulingController, PlanUpdatedMessage::class.java)
        actorSystem.eventStream().subscribe(schedulingController, TaskAppearedMessage::class.java)

        modificationController = actorSystem.actorOf(Props.create(ModificationController::class.java, taskSupervisor, resourceSupervisor))
    }

    private fun fillAgentToEntityClassMapping(): BiMap<Class<out IdentifiableObject>, Class<out UntypedActor>> {
        agentToEntityTypeMap.put(Resource::class.java, ResourceActor::class.java)
        agentToEntityTypeMap.put(Task::class.java, TaskActor::class.java)
        return agentToEntityTypeMap
    }

    override fun run() {
        initAndStart()
    }

    private fun initAndStart() {
        LOG.info("Scheduling init started")
        removeExistedActors()
        createSystemAgents()
        notifyModificationControllerAboutExistedEntities()
        createDataSourceModificationControllerListener()
    }

    private fun notifyModificationControllerAboutExistedEntities() {
        for (identifiableObject in dataSource) {
            val entityCreatedMessage = EntityCreatedMessage(identifiableObject)
            modificationController!!.tell(entityCreatedMessage, ActorRef.noSender())
        }
    }

    override fun reset() {
        removeExistedActors()
    }

    private fun removeExistedActors() {
        for (actorRef in agentToEntityMap.values) {
            actorSystem.stop(actorRef)
        }
        agentToEntityMap.clear()

        if (modificationController != null) {
            actorSystem.stop(modificationController)
            modificationController = null
        }

        if (taskSupervisor != null) {
            actorSystem.stop(taskSupervisor)
            taskSupervisor = null
        }

        if (resourceSupervisor != null) {
            actorSystem.stop(resourceSupervisor)
            resourceSupervisor = null
        }

        if (schedulingController != null) {
            actorSystem.stop(schedulingController)
            schedulingController = null
        }
    }

    override fun addStablePlanListener(listener: PlanRepresentative.StablePlanListener) {
        mergingController.addStablePlanListener(listener)
    }

    override fun removeStablePlanListener(listener: PlanRepresentative.StablePlanListener) {
        mergingController.removeStablePlanListener(listener)
    }
}
