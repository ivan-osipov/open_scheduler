package org.pyjjs.scheduler.core.api.impl.actors.common;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public abstract class ActorState {

    private ActorContext actorContext;

    private boolean initialized = false;

    public ActorState(ActorContext actorContext) {
        this.actorContext = actorContext;
    }

    public ActorRef getActorRef() {
        return actorContext.self();
    }

    public ActorSystem getActorSystem() {
        return actorContext.system();
    }

    public ActorContext getActorContext() {
        return actorContext;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    protected abstract ActorState copySelf();

}
