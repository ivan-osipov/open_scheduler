package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.messages.TaskInitMessage;

public class TaskInitBehaviour extends Behaviour<TaskActorState, TaskInitMessage> {

    @Override
    protected void perform(TaskInitMessage message) {
        TaskActorState taskActorState = getActorState();
        taskActorState.setSource(message.getSource());
        taskActorState.setInitialized(true);
        saveActorState(taskActorState);
    }

    @Override
    protected Class<TaskInitMessage> processMessage() {
        return TaskInitMessage.class;
    }
}
