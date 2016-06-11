package org.pyjjs.scheduler.core.actors.system.messages;

import akka.actor.ActorRef;

public class EntityRemovedMessage extends DataSourceChangedMessage {

    public EntityRemovedMessage(ActorRef sender) {
        super(sender);
    }
}
