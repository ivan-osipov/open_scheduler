package org.pyjjs.scheduler.core.actors.task.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.messages.Message;

public class IFindSomeResourceMessage extends Message {

    public IFindSomeResourceMessage(ActorRef sender) {
        super(sender);
    }
}
