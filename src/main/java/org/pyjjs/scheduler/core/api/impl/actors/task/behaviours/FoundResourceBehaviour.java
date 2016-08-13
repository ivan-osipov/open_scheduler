package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.FoundResourceMessage;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState;

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
