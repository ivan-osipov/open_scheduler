package org.pyjjs.scheduler.core.actors.task.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.messages.Message;

public class IFindResourceMessage extends Message {

    public IFindResourceMessage(ActorRef sender) {
        super(sender);
    }
}
