package org.pyjjs.scheduler.core.actors.task.supervisor;

import org.pyjjs.scheduler.core.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.task.supervisor.behaviours.CreateTaskBehaviour;
import org.pyjjs.scheduler.core.actors.task.supervisor.behaviours.ResourceAppearedSupervisorBehaviour;

public class TaskSupervisor extends BehaviourBasedActor<TaskSupervisorState> {

    @Override
    protected TaskSupervisorState getInitialState() {
        return new TaskSupervisorState(getContext());
    }

    @Override
    protected void init() {
        fillBehaviours();
    }

    private void fillBehaviours() {
        addBehaviour(CreateTaskBehaviour.class);
        addBehaviour(ResourceAppearedSupervisorBehaviour.class);
    }
}
