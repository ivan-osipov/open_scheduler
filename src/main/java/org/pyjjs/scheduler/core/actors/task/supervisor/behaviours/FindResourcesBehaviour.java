package org.pyjjs.scheduler.core.actors.task.supervisor.behaviours;

import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;
import org.pyjjs.scheduler.core.actors.task.supervisor.TaskSupervisorState;

public class FindResourcesBehaviour extends Behaviour<TaskSupervisorState, IFindSomeResourceMessage> {
    private static final FindResourcesBehaviour INSTANCE = new FindResourcesBehaviour();

    @Override
    protected void perform(IFindSomeResourceMessage message) {
        send(getActorState().getResourceSupervisor(), message);
    }

    @Override
    protected Class<IFindSomeResourceMessage> processMessage() {
        return IFindSomeResourceMessage.class;
    }

    public static FindResourcesBehaviour get() {
        return INSTANCE;
    }
}
