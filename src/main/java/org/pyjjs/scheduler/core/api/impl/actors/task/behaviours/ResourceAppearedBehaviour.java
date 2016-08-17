package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.messages.ResourceAppearedMessage;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.api.impl.actors.task.messages.IFindAnyPlacementMessage;

public class ResourceAppearedBehaviour extends TaskBehaviour<ResourceAppearedMessage> {

    @Override
    protected void perform(ResourceAppearedMessage message) {
        TaskActorState state = getActorState();
        if(hasNotPlacement(state)) {
            send(message.getResourceRef(), new IFindAnyPlacementMessage(getActorRef(), state.getSource().getResourceCriteria()));
        }
    }

    private boolean hasNotPlacement(TaskActorState taskActorState) {
        return taskActorState.getDiscontent() == null;
    }

    @Override
    protected Class<ResourceAppearedMessage> processMessage() {
        return ResourceAppearedMessage.class;
    }
}
