package org.pyjjs.scheduler.core.actors.common;

import akka.actor.UntypedActor;

import java.util.logging.Logger;

public abstract class StateOrientedActor<T extends ActorState> extends UntypedActor{
    protected static Logger LOG = Logger.getLogger(StateOrientedActor.class.getName());

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
