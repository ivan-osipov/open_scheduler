package org.pyjjs.scheduler.core.actors.task;

import org.pyjjs.scheduler.core.actors.common.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.task.behaviours.FoundResourceBehaviour;
import org.pyjjs.scheduler.core.actors.task.behaviours.ResourceInfoStoreBehaviour;
import org.pyjjs.scheduler.core.actors.task.behaviours.TaskInitBehaviour;

public class TaskActor extends BehaviourBasedActor<TaskActorState> {

    @Override
    protected TaskActorState getInitialState() {
        return new TaskActorState(getContext());
    }

    @Override
    protected void init() {
        fillBehaviours();
        LOG.info("Task Actor started: " + this.getSelf().path().toString());
    }

    private void fillBehaviours() {
        addBehaviour(TaskInitBehaviour.get());
        addBehaviour(FoundResourceBehaviour.get());
        addBehaviour(ResourceInfoStoreBehaviour.get());
    }
}
