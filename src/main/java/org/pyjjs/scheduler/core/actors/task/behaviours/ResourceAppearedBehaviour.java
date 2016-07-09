package org.pyjjs.scheduler.core.actors.task.behaviours;

import org.pyjjs.scheduler.core.actors.resource.supervisor.messages.ResourceAppearedMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.actors.task.messages.IFindResourceMessage;

public class ResourceAppearedBehaviour extends TaskBehaviour<ResourceAppearedMessage> {

    @Override
    protected void perform(ResourceAppearedMessage message) {
        TaskActorState state = getActorState();
        Double discontent = state.getDiscontent();
        if(discontent == null || discontent != 0) {
            sendToResources(new IFindResourceMessage(getActorRef()));
        }
    }

    @Override
    protected Class<ResourceAppearedMessage> processMessage() {
        return ResourceAppearedMessage.class;
    }
}
