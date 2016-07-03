package org.pyjjs.scheduler.core.actors.common.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.model.IdentifiableObject;

public class InitEntityAgentMessage<T extends IdentifiableObject> extends Message {

    private T source;

    public InitEntityAgentMessage(ActorRef sender, T source) {
        super(sender);
        this.source = source;
    }

    public T getSource() {
        return source;
    }
}
