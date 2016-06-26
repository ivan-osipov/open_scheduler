package org.pyjjs.scheduler.core.actors.common;

import akka.actor.ActorContext;

public abstract class StatelessBehaviour <M extends Message> {

    private ActorContext actorContext;

    protected StatelessBehaviour(ActorContext actorContext) {
        this.actorContext = actorContext;
    }

    public ActorContext getActorContext() {
        return actorContext;
    }

    protected abstract void perform(M message);

    protected void answer(M inMessage, Message answerMessage) {
        inMessage.getSender().tell(answerMessage, actorContext.self());
    }

    protected abstract Class<M> processMessage();
}
