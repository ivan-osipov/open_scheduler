package org.pyjjs.scheduler.core.actors.common;

import akka.actor.ActorRef;

public abstract class StatelessBehaviour <M extends Message> {

    protected ActorRef actorRef;

    protected StatelessBehaviour(ActorRef actorRef) {
        this.actorRef = actorRef;
    }

    protected abstract void perform(M message);

    protected void answer(M inMessage, Message answerMessage) {
        inMessage.getSender().tell(answerMessage, actorRef);
    }

    protected abstract Class<M> processMessage();
}
