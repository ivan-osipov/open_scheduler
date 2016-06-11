package org.pyjjs.scheduler.core.api.microeconomic;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.pyjjs.scheduler.core.actors.system.ModificationController;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityRemovedMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityUpdatedMessage;
import org.pyjjs.scheduler.core.api.Plan;
import org.pyjjs.scheduler.core.actors.resource.ResourceActor;
import org.pyjjs.scheduler.core.actors.task.TaskActor;
import org.pyjjs.scheduler.core.api.PlanCompleteListener;
import org.pyjjs.scheduler.core.api.Scheduler;
import org.pyjjs.scheduler.core.data.HashSetDataSourceImpl;
import org.pyjjs.scheduler.core.data.ObservableDataSource;
import org.pyjjs.scheduler.core.model.primary.IdentifiableObject;
import org.pyjjs.scheduler.core.model.primary.Resource;
import org.pyjjs.scheduler.core.model.primary.Task;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class MicroeconomicsBasedMultiAgentScheduler implements Scheduler, Closeable {

    private ObservableDataSource dataSource;

    private ActorSystem actorSystem;

    private Map<IdentifiableObject, ActorRef> agentToEntityMap = new HashMap<>();

    private BiMap<Class<? extends IdentifiableObject>, Class<? extends UntypedActor>> agentToEntityTypeMap = HashBiMap.create();

    private volatile boolean running = false;

    private Set<PlanCompleteListener> planCompleteListeners = new HashSet<>();

    private ActorRef modificationController;


    public MicroeconomicsBasedMultiAgentScheduler(@Nonnull ObservableDataSource dataSource) {
        this.dataSource = dataSource;
        prepareAndLaunch();
    }

    private void prepareAndLaunch() {
        fillAgentToEntityClassMapping();
        actorSystem = ActorSystem.apply("scheduler");
    }

    private void createDataSourceModificationControllerListener() {
        List<Class<? extends IdentifiableObject>> interests = Arrays.asList(Task.class, Resource.class);
        this.dataSource.addDataSourceListener(interests ,new ObservableDataSource.DataSourceListener() {

            @Override
            public void onCreate(IdentifiableObject entity) {
                EntityCreatedMessage msg = new EntityCreatedMessage(ActorRef.noSender());
                msg.setEntity(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }

            @Override
            public void onUpdate(IdentifiableObject entity) {
                EntityUpdatedMessage msg = new EntityUpdatedMessage(ActorRef.noSender());
                msg.setEntity(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }

            @Override
            public void onRemove(IdentifiableObject entity) {
                EntityRemovedMessage msg = new EntityRemovedMessage(ActorRef.noSender());
                msg.setEntity(entity);
                modificationController.tell(msg, ActorRef.noSender());
            }
        });
    }

    private void createSystemAgents() {
        modificationController = actorSystem.actorOf(Props.create(ModificationController.class));
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
    }

    private Map<IdentifiableObject, ActorRef> fillActorMapByDataSource() {
        for (IdentifiableObject entity : dataSource) {
            Class<? extends UntypedActor> actorType = agentToEntityTypeMap.get(entity.getClass());
            checkNotNull(actorType, "In data source found entity not extended Resource or Task");
            ActorRef entityActor = createActorByType(actorType);
            agentToEntityMap.put(entity, entityActor);
        }
        return agentToEntityMap;
    }

    private ActorRef createActorByType(Class<? extends UntypedActor> actorType) {
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
