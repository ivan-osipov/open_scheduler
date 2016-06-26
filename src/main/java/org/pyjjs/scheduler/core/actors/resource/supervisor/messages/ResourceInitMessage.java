package org.pyjjs.scheduler.core.actors.resource.supervisor.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.messages.InitEntityAgentMessage;
import org.pyjjs.scheduler.core.model.primary.Resource;

public class ResourceInitMessage extends InitEntityAgentMessage<Resource> {
    public ResourceInitMessage(ActorRef sender, Resource source) {
        super(sender, source);
    }
}
