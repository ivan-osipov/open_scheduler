package org.pyjjs.scheduler.core.api.microeconomic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.pyjjs.scheduler.core.actors.common.SystemHelper;
import org.pyjjs.scheduler.core.actors.resource.supervisor.ResourceSupervisor;
import org.pyjjs.scheduler.core.actors.system.ModificationController;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityRemovedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityUpdatedMessage;
import org.pyjjs.scheduler.core.actors.task.supervisor.TaskSupervisor;
import org.pyjjs.scheduler.core.api.Plan;
import org.pyjjs.scheduler.core.actors.resource.ResourceActor;
import org.pyjjs.scheduler.core.actors.task.TaskActor;
import org.pyjjs.scheduler.core.api.PlanCompleteListener;
import org.pyjjs.scheduler.core.api.Scheduler;
import org.pyjjs.scheduler.core.data.HashSetDataSourceImpl;
import org.pyjjs.scheduler.core.data.ObservableDataSource;
import org.pyjjs.scheduler.core.model.IdentifiableObject;
import org.pyjjs.scheduler.core.model.Resource;
import org.pyjjs.scheduler.core.model.Task;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class MicroeconomicsBasedMultiAgentScheduler implements Scheduler, Closeable {

    private static final String DEFAULT_SCHEDULER_CONFIG_PATH = "default_scheduler_config.properties";
    private ObservableDataSource dataSource;

    private ActorSystem actorSystem;

    private Map<IdentifiableObject, ActorRef> agentToEntityMap = new HashMap<>();

    private BiMap<Class<? extends IdentifiableObject>, Class<? extends UntypedActor>> agentToEntityTypeMap = HashBiMap.create();

    private volatile boolean running = false;

    private Set<PlanCompleteListener> planCompleteListeners = new HashSet<>();

    private ActorRef modificationController;

    private ActorRef taskSupervisor;

    private ActorRef resourceSupervisor;


    public MicroeconomicsBasedMultiAgentScheduler(@Nonnull ObservableDataSource dataSource) {
        this.dataSource = dataSource;
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

        modificationController = actorSystem.actorOf(Props.create(ModificationController.class, taskSupervisor, resourceSupervisor));
        createDataSourceModificationControllerListener();

    }

    public MicroeconomicsBasedMultiAgentScheduler() {
        this.dataSource = new HashSetDataSourceImpl();
        prepareAndLaunch();
    }

    private BiMap<Class<? extends IdentifiableObject>, Class<? extends UntypedActor>> fillAgentToEntityClassMapping() {
        agentToEntityTypeMap.put(Resource.class, ResourceActor.class);
        agentToEntityTypeMap.put(Task.class, TaskActor.class);
        return agentToEntityTypeMap;
    }

    @Override
    public void asyncRun(PlanCompleteListener... listeners) {
        updateAgentToEntityMapFromDataSource();
        createSystemAgents();
        //TODO WAIT PLAN
        Plan plan = null;
        notifyPlanListeners(plan);

    }

    private void notifyPlanListeners(Plan plan) {
        planCompleteListeners.stream().forEach(listener -> listener.onPlanComplete(plan));
    }

    @Override
    public Plan syncRun() {
        updateAgentToEntityMapFromDataSource();
        createSystemAgents();
        return null;
    }

    private void updateAgentToEntityMapFromDataSource() {
        removeExistedActors();
        fillActorMapByDataSource();
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
            modificationController = null;
        }
    }

    private Map<IdentifiableObject, ActorRef> fillActorMapByDataSource() {
        for (IdentifiableObject entity : dataSource) {
            Class<? extends UntypedActor> actorType = agentToEntityTypeMap.get(entity.getClass());
            checkNotNull(actorType, "In data source found entity not extended Resource or Task");
            ActorRef entityActor = SystemHelper.createActorByType(actorSystem, actorType);
            agentToEntityMap.put(entity, entityActor);
        }
        return agentToEntityMap;
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
    public void addPlanCompleteListener(PlanCompleteListener listener) {
        planCompleteListeners.add(listener);
    }

    @Override
    public void removePlanCompleteListener(PlanCompleteListener listener) {
        planCompleteListeners.remove(listener);
    }

    @Override
    public void close() throws IOException {
        reset();
    }
}
