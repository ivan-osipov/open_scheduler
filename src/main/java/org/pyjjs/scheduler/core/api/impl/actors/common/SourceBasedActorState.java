package org.pyjjs.scheduler.core.api.impl.actors.common;

import akka.actor.ActorContext;

public class SourceBasedActorState<T> extends ActorState {

    private T source;

    public SourceBasedActorState(ActorContext actorContext) {
        super(actorContext);
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
