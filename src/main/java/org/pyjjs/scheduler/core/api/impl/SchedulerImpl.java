package org.pyjjs.scheduler.core.api.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.jetbrains.annotations.NotNull;
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActor;
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.ResourceSupervisor;
import org.pyjjs.scheduler.core.api.impl.actors.system.ModificationController;
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingController;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.*;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActor;
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.TaskSupervisor;
import org.pyjjs.scheduler.core.api.PlanMergingController;
import org.pyjjs.scheduler.core.api.PlanRepresentative;
import org.pyjjs.scheduler.core.api.Scheduler;
import org.pyjjs.scheduler.core.data.HashSetDataSourceImpl;
import org.pyjjs.scheduler.core.data.ObservableDataSource;
import org.pyjjs.scheduler.core.model.IdentifiableObject;
import org.pyjjs.scheduler.core.model.Resource;
import org.pyjjs.scheduler.core.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class SchedulerImpl implements Scheduler {

    private static final String DEFAULT_SCHEDULER_CONFIG_PATH = "default_scheduler_config.properties";

    private UUID uuid = UUID.randomUUID();

    private ObservableDataSource dataSource;

    private ActorSystem actorSystem;

    private Map<IdentifiableObject, ActorRef> agentToEntityMap = new HashMap<>();

    private BiMap<Class<? extends IdentifiableObject>, Class<? extends UntypedActor>> agentToEntityTypeMap = HashBiMap.create();

    private volatile boolean running = false;

    private ActorRef schedulingController;

    private ActorRef modificationController;

    private ActorRef taskSupervisor;

    private ActorRef resourceSupervisor;

    private PlanMergingController mergingController;

    private Logger LOG = LoggerFactory.getLogger(String.format("Scheduler [%s]", uuid.toString()));


    public SchedulerImpl() {
        this(new HashSetDataSourceImpl());
    }

    public SchedulerImpl(@Nonnull ObservableDataSource dataSource) {
        this.dataSource = dataSource;
        mergingController = new PlanMergingController();
        prepareAndLaunch();
    }

    private void prepareAndLaunch() {
        fillAgentToEntityClassMapping();
        Config config = ConfigFactory.load(DEFAULT_SCHEDULER_CONFIG_PATH);
        actorSystem = ActorSystem.apply("scheduler", config);
    }

    private void createDataSourceModificationControllerListener() {
        List<Class<? extends IdentifiableObject>> interests = Arrays.asList(Task.class, Resource.class);
        this.dataSource.addDataSourceListener(interests, new ObservableDataSource.DataSourceListener() {

            @Override
            public void onCreate(IdentifiableObject entity) {
                EntityCreatedMessage msg = new EntityCreatedMessage(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }

            @Override
            public void onUpdate(IdentifiableObject entity) {
                EntityUpdatedMessage msg = new EntityUpdatedMessage(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }

            @Override
            public void onRemove(IdentifiableObject entity) {
                EntityRemovedMessage msg = new EntityRemovedMessage(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }
        });
    }

    private void createSystemAgents() {
        taskSupervisor = actorSystem.actorOf(Props.create(TaskSupervisor.class), "tasks");
        resourceSupervisor = actorSystem.actorOf(Props.create(ResourceSupervisor.class), "resources");

        schedulingController = actorSystem.actorOf(Props.create(SchedulingController.class, mergingController));
        actorSystem.eventStream().subscribe(schedulingController, PlanUpdatedMessage.class);

        modificationController = actorSystem.actorOf(Props.create(ModificationController.class, taskSupervisor, resourceSupervisor));
    }

    private BiMap<Class<? extends IdentifiableObject>, Class<? extends UntypedActor>> fillAgentToEntityClassMapping() {
        agentToEntityTypeMap.put(Resource.class, ResourceActor.class);
        agentToEntityTypeMap.put(Task.class, TaskActor.class);
        return agentToEntityTypeMap;
    }

    @Override
    public void run() {
        initAndStart();
    }

    private void initAndStart() {
        LOG.info("Scheduling init started");
        removeExistedActors();
        createSystemAgents();
        notifyModificationControllerAboutExistedEntities();
        createDataSourceModificationControllerListener();
    }

    private void notifyModificationControllerAboutExistedEntities() {
        for (IdentifiableObject identifiableObject : dataSource) {
            EntityCreatedMessage entityCreatedMessage = new EntityCreatedMessage(identifiableObject);
            modificationController.tell(entityCreatedMessage, ActorRef.noSender());
        }
    }

    private Map<IdentifiableObject, ActorRef> fillActorMapByDataSource() {
        for (IdentifiableObject entity : dataSource) {
            Class<? extends UntypedActor> actorType = agentToEntityTypeMap.get(entity.getClass());
            checkNotNull(actorType, "In data source found entity not extended Resource or Task");
            ActorRef entityActor = createActorByType(actorSystem, actorType);
            agentToEntityMap.put(entity, entityActor);
        }
        return agentToEntityMap;
    }

    private static ActorRef createActorByType(ActorSystem actorSystem, Class<? extends UntypedActor> actorType) {
        return actorSystem.actorOf(Props.create(actorType));
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void reset() {
        removeExistedActors();
    }

    private void removeExistedActors() {
        for (ActorRef actorRef : agentToEntityMap.values()) {
            actorSystem.stop(actorRef);
        }
        agentToEntityMap.clear();

        if(modificationController != null) {
            actorSystem.stop(modificationController);
            modificationController = null;
        }

        if(taskSupervisor != null) {
            actorSystem.stop(taskSupervisor);
            taskSupervisor = null;
        }

        if(resourceSupervisor != null) {
            actorSystem.stop(resourceSupervisor);
            resourceSupervisor = null;
        }

        if(schedulingController != null) {
            actorSystem.stop(schedulingController);
            schedulingController = null;
        }
    }

    @NotNull
    @Override
    public ObservableDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void addStablePlanListener(@Nonnull PlanRepresentative.StablePlanListener listener) {
        mergingController.addStablePlanListener(listener);
    }

    @Override
    public void removeStablePlanListener(@Nonnull PlanRepresentative.StablePlanListener listener) {
        mergingController.removeStablePlanListener(listener);
    }
}
