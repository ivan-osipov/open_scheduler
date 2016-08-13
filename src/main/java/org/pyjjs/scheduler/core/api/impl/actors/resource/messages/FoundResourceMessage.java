package org.pyjjs.scheduler.core.api.impl.actors.resource.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;

public class FoundResourceMessage extends Message {

    public FoundResourceMessage(ActorRef sender) {
        super(sender);
    }
}
