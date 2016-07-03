package org.pyjjs.scheduler.core.actors.task.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.Message;

public class CheckOffersMessage extends Message {
    public CheckOffersMessage(ActorRef sender) {
        super(sender);
    }
}
