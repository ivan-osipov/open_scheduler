package org.pyjjs.scheduler.core.actors.system.messages;

import akka.actor.ActorRef;

public class EntityCreatedMessage extends DataSourceChangedMessage {

    public EntityCreatedMessage(ActorRef sender) {
        super(sender);
    }
}
