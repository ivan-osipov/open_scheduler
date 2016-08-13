package org.pyjjs.scheduler.core.api.impl.actors.system.messages;

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;
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
