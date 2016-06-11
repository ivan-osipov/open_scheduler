package org.pyjjs.scheduler.core.actors.common;

import akka.actor.ActorRef;

public abstract class Behaviour<T extends ActorState, M extends Message> {

    protected Behaviour(){}

    protected abstract void perform(M message);

    protected void answer(M inMessage, Message answerMessage) {
        inMessage.getSender().tell(answerMessage, getActorRef());
    }

    protected void broadcast(Message message) {

    }

    protected ActorRef getActorRef() {
        return getActorState().getActorRef();
    }

    private T actorState;

    public T getActorState() {
        return actorState;
    }

    public void setActorState(T actorState) {
        ActorState actorStateCopy = actorState.copySelf();
        this.actorState = (T) actorStateCopy;
    }

    protected abstract Class<M> processMessage();
}
