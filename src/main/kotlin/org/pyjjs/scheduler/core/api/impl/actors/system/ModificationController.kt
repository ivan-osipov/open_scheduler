package org.pyjjs.scheduler.core.api.impl.actors.system

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.DataSourceChangeBehaviour
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import scala.concurrent.Promise

class ModificationController() : BehaviourBasedActor<ModificationControllerState>() {

    var taskSupervisor: ActorRef? = null
    var resourceSupervisor: ActorRef? = null
    constructor(taskSupervisor: ActorRef?, resourceSupervisor: ActorRef?) : this() {
        this.taskSupervisor = taskSupervisor
        this.resourceSupervisor = resourceSupervisor
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Modification controller is initialized")
    }

    private fun fillBehaviours() {
        addBehaviour(DataSourceChangeBehaviour::class.java)
    }

    override fun createInitialState(): ModificationControllerState {
        val modificationControllerState = ModificationControllerState(context, taskSupervisor!!, resourceSupervisor!!)
        modificationControllerState.isInitialized = true
        return modificationControllerState
    }
}
