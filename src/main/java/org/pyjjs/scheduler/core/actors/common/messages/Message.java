package org.pyjjs.scheduler.core.actors.common.messages;

import akka.actor.ActorRef;

public class Message {
    private ActorRef sender;

    public Message() {
        this(ActorRef.noSender());
    }

    public Message(ActorRef sender) {
        this.sender = sender;
    }

    public ActorRef getSender() {
        return sender;
    }
}
