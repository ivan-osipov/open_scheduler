package org.pyjjs.scheduler.core.api.microeconomic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.pyjjs.scheduler.core.actors.resource.ResourceActor;
import org.pyjjs.scheduler.core.actors.resource.supervisor.ResourceSupervisor;
import org.pyjjs.scheduler.core.actors.system.ModificationController;
import org.pyjjs.scheduler.core.actors.system.SchedulingController;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityRemovedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityUpdatedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.PlanUpdatedMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActor;
import org.pyjjs.scheduler.core.actors.task.supervisor.TaskSupervisor;
import org.pyjjs.scheduler.core.api.*;
import org.pyjjs.scheduler.core.data.HashSetDataSourceImpl;
import org.pyjjs.scheduler.core.data.ObservableDataSource;
import org.pyjjs.scheduler.core.model.IdentifiableObject;
import org.pyjjs.scheduler.core.model.Resource;
import org.pyjjs.scheduler.core.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.google.common.base.Preconditions.checkNotNull;

public class MicroeconomicsBasedMultiAgentScheduler implements Scheduler, Closeable {

    private static final String DEFAULT_SCHEDULER_CONFIG_PATH = "default_scheduler_config.properties";

    private UUID uuid = UUID.randomUUID();

    private ExecutorService schedulerRunner = Executors.newSingleThreadScheduledExecutor();

    private ObservableDataSource dataSource;

    private ActorSystem actorSystem;

    private Map<IdentifiableObject, ActorRef> agentToEntityMap = new HashMap<>();

    private BiMap<Class<? extends IdentifiableObject>, Class<? extends UntypedActor>> agentToEntityTypeMap = HashBiMap.create();

    private volatile boolean running = false;

    private Set<PlanRepresentative.StablePlanListener> stablePlanListeners = new HashSet<>();

    private ActorRef schedulingController;

    private ActorRef modificationController;

    private ActorRef taskSupervisor;

    private ActorRef resourceSupervisor;

    private PlanMergingController mergingController;

    private Logger LOG = LoggerFactory.getLogger(String.format("Scheduler [%s]", uuid.toString()));


    public MicroeconomicsBasedMultiAgentScheduler(@Nonnull ObservableDataSource dataSource) {
        this.dataSource = dataSource;
        mergingController = new PlanMergingController();
        prepareAndLaunch();
    }

    public MicroeconomicsBasedMultiAgentScheduler() {
        this(new HashSetDataSourceImpl());
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
                EntityCreatedMessage msg = new EntityCreatedMessage();
                msg.setEntity(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }

            @Override
            public void onUpdate(IdentifiableObject entity) {
                EntityUpdatedMessage msg = new EntityUpdatedMessage();
                msg.setEntity(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }

            @Override
            public void onRemove(IdentifiableObject entity) {
                EntityRemovedMessage msg = new EntityRemovedMessage();
                msg.setEntity(entity);
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
    public void run(PlanCompleteListener... listeners) {
//        schedulerRunner.submit(this::initAndStart);
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
            EntityCreatedMessage entityCreatedMessage = new EntityCreatedMessage();
            entityCreatedMessage.setEntity(identifiableObject);
            modificationController.tell(entityCreatedMessage, ActorRef.noSender());
        }
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

    @Override
    public ObservableDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void addStablePlanListener(@Nonnull PlanRepresentative.StablePlanListener listener) {
        stablePlanListeners.add(listener);
    }

    @Override
    public void removePlanCompleteListener(@Nonnull PlanRepresentative.StablePlanListener listener) {
        stablePlanListeners.remove(listener);
    }

    @Override
    public void close() throws IOException {
        reset();
    }
}
