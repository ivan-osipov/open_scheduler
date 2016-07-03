package org.pyjjs.scheduler.core.actors.task.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.messages.Message;

public class CheckOffersMessage extends Message {
    public CheckOffersMessage(ActorRef sender) {
        super(sender);
    }
}
