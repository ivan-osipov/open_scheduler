package org.pyjjs.scheduler.core.actors.system.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.Message;
import org.pyjjs.scheduler.core.model.IdentifiableObject;

public class DataSourceChangedMessage extends Message {

    protected IdentifiableObject entity;

    public DataSourceChangedMessage(ActorRef sender) {
        super(sender);
    }

    public IdentifiableObject getEntity() {
        return entity;
    }

    public void setEntity(IdentifiableObject entity) {
        this.entity = entity;
    }
}
