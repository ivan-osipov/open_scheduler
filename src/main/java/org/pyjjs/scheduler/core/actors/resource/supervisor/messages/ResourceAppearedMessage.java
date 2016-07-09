package org.pyjjs.scheduler.core.actors.resource.supervisor.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.messages.Message;
import org.pyjjs.scheduler.core.model.Resource;

public class ResourceAppearedMessage extends Message {

    private Resource resource;
    private ActorRef resourceRef;

    public ResourceAppearedMessage(ActorRef resourceRef, Resource resource, ActorRef sender) {
        super(sender);
        this.resourceRef = resourceRef;
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public ActorRef getResourceRef() {
        return resourceRef;
    }

    public void setResourceRef(ActorRef resourceRef) {
        this.resourceRef = resourceRef;
    }
}
