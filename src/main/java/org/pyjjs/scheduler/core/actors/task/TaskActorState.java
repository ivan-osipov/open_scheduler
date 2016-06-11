package org.pyjjs.scheduler.core.actors.task;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.ActorState;

public class TaskActorState extends ActorState {

    public TaskActorState(ActorRef actorRef) {
        super(actorRef);
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
