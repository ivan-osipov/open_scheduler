package org.pyjjs.scheduler.core.actors.common;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;

public abstract class Behaviour<T extends ActorState, M extends Message> {

    private ActorStateInteraction<T> actorStateInteraction;

    private T actorState;

    protected Behaviour(){}

    protected abstract void perform(M message);

    protected void answer(M inMessage, Message answerMessage) {
        inMessage.getSender().tell(answerMessage, getActorRef());
    }

    protected void send(ActorRef receiver, Message message) {
        receiver.tell(message, getActorRef());
    }

    protected ActorRef getActorRef() {
        return getActorState().getActorRef();
    }

    protected void saveActorState(T actorState) {
        actorStateInteraction.saveActorState(actorState);
    }

    public T getActorState() {
        return actorState;
    }

    public void setActorStateInteraction(ActorStateInteraction<T> actorStateInteraction) {
        this.actorStateInteraction = actorStateInteraction;
        this.actorState = actorStateInteraction.getActorState();
    }

    public void sendToParent(Message message) {
        getActorState().getActorContext().parent().tell(message, getActorRef());
    }

    public void sendToResources(Message message) {
        getActorState().getActorContext()
                .system()
                .actorSelection("user/resources/*")
                .tell(message, getActorRef());
    }

    public void sendToTasks(Message message) {
        getActorState().getActorContext()
                .system()
                .actorSelection("user/tasks/*")
                .tell(message, getActorRef());
    }

    protected abstract Class<M> processMessage();
}
