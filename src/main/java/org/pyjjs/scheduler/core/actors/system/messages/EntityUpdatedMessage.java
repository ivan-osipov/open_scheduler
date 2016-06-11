package org.pyjjs.scheduler.core.actors.system.messages;

import akka.actor.ActorRef;

public class EntityUpdatedMessage extends DataSourceChangedMessage {

    public EntityUpdatedMessage(ActorRef sender) {
        super(sender);
    }
}
