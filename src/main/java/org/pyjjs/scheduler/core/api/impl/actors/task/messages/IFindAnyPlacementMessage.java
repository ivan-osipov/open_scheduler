package org.pyjjs.scheduler.core.api.impl.actors.task.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;
import org.pyjjs.scheduler.core.model.ResourceCriteria;

public class IFindAnyPlacementMessage extends Message {

    private ResourceCriteria resourceCriteria = new ResourceCriteria();

    public IFindAnyPlacementMessage(ActorRef sender, ResourceCriteria resourceCriteria) {
        super(sender);
        this.resourceCriteria = resourceCriteria;
    }

    public ResourceCriteria getResourceCriteria() {
        return resourceCriteria;
    }

    public void setResourceCriteria(ResourceCriteria resourceCriteria) {
        this.resourceCriteria = resourceCriteria;
    }
}
