package org.pyjjs.scheduler.core.actors.common;

import akka.actor.ActorRef;

public abstract class ActorState {

    private ActorRef actorRef;

    public ActorState(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    public ActorRef getActorRef() {
        return actorRef;
    }

    protected abstract ActorState copySelf();

}
