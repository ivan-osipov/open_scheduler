package org.pyjjs.scheduler.core.api.impl.actors.common.behaviours;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;

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
