package org.pyjjs.scheduler.core.actors.resource;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.ActorState;

public class ResourceActorState extends ActorState{

    enum Status { CREATED, RECEIVE_REQUEST, DONE;}

    protected ResourceActorState(ActorRef actorRef) {
        super(actorRef);
    }

    @Override
    public ActorState copySelf() {
        return this;
    }


}
