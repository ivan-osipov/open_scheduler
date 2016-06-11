package org.pyjjs.scheduler.core.actors.resource.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.Message;

public class FoundResourceMessage extends Message {

    public FoundResourceMessage(ActorRef sender) {
        super(sender);
    }
}
