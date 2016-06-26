package org.pyjjs.scheduler.core.actors.task.behaviours;

import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;
import org.pyjjs.scheduler.core.actors.task.supervisor.messages.TaskInitMessage;

public class TaskInitBehaviour extends Behaviour<TaskActorState, TaskInitMessage> {

    @Override
    protected void perform(TaskInitMessage message) {
        TaskActorState taskActorState = getActorState();
        taskActorState.setSource(message.getSource());
        taskActorState.setInitialized(true);
        saveActorState(taskActorState);

        sendToResources(new IFindSomeResourceMessage(getActorRef()));
    }

    @Override
    protected Class<TaskInitMessage> processMessage() {
        return TaskInitMessage.class;
    }
}
