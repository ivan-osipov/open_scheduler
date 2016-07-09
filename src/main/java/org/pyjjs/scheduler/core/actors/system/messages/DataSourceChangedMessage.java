package org.pyjjs.scheduler.core.actors.system.messages;

import org.pyjjs.scheduler.core.actors.common.messages.Message;
import org.pyjjs.scheduler.core.model.IdentifiableObject;

public class DataSourceChangedMessage extends Message {

    protected IdentifiableObject entity;

    public IdentifiableObject getEntity() {
        return entity;
    }

    public void setEntity(IdentifiableObject entity) {
        this.entity = entity;
    }
}
