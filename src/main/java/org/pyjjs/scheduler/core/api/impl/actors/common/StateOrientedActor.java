package org.pyjjs.scheduler.core.api.impl.actors.common;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StateOrientedActor<T extends ActorState> extends UntypedActor{
    protected static Logger LOG = LoggerFactory.getLogger(StateOrientedActor.class.getName());

    private T actorState = getInitialState();

    protected void updateActorState(T actorState) {
        this.actorState = actorState;
    }

    @SuppressWarnings("unchecked")
    protected T getCopyOfActorState() {
        return (T) actorState.copySelf();
    }

    protected abstract T getInitialState();
}
