package org.pyjjs.scheduler.core.api.impl.actors.task.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;

public class IFindAnyPlacementMessage extends Message {

    public IFindAnyPlacementMessage(ActorRef sender) {
        super(sender);
    }
}
