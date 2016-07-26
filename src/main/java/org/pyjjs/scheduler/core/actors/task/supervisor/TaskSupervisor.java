package org.pyjjs.scheduler.core.actors.task.supervisor;

import org.pyjjs.scheduler.core.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.task.supervisor.behaviours.CreateTaskBehaviour;
import org.pyjjs.scheduler.core.actors.task.supervisor.behaviours.ResourceAppearedSupervisorBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskSupervisor extends BehaviourBasedActor<TaskSupervisorState> {

    private static final Logger LOG = LoggerFactory.getLogger(TaskSupervisor.class);

    @Override
    protected TaskSupervisorState getInitialState() {
        return new TaskSupervisorState(getContext());
    }

    @Override
    protected void init() {
        fillBehaviours();
        LOG.info("Task supervisor is initialized");
    }

    private void fillBehaviours() {
        addBehaviour(CreateTaskBehaviour.class);
        addBehaviour(ResourceAppearedSupervisorBehaviour.class);
    }
}
