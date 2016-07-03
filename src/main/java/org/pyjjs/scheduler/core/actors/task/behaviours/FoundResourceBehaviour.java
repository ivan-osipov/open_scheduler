package org.pyjjs.scheduler.core.actors.task.behaviours;

import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.messages.FoundResourceMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActorState;

public class FoundResourceBehaviour extends Behaviour<TaskActorState, FoundResourceMessage> {

    @Override
    public void perform(FoundResourceMessage message) {
        System.out.println("Resource found");
    }

    @Override
    protected Class<FoundResourceMessage> processMessage() {
        return FoundResourceMessage.class;
    }
}
