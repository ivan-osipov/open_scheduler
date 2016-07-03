package org.pyjjs.scheduler.core.actors.common.supervisor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.model.IdentifiableObject;

import java.util.HashMap;
import java.util.Map;

public abstract class SupervisorState<T extends IdentifiableObject> extends ActorState{

    private Map<T, ActorRef> actorsBySource = new HashMap<>();

    public SupervisorState(ActorContext actorContext) {
        super(actorContext);
    }

    public void registerTaskActor(T entity, ActorRef actorRef) {
        actorsBySource.put(entity, actorRef);
    }

    public void unregisterTaskActor(T entity) {
        actorsBySource.remove(entity);
    }

    public ActorRef findBySource(T entity) {
        return actorsBySource.get(entity);
    }

}
