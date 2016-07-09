package org.pyjjs.scheduler.core.actors.task;

import org.pyjjs.scheduler.core.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.task.behaviours.*;

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
        addBehaviour(TaskInitBehaviour.class);
        addBehaviour(ResourceAppearedBehaviour.class);
        addBehaviour(FoundResourceBehaviour.class);
        addBehaviour(OfferStoreBehaviour.class);
        addBehaviour(OffersCheckBehaviour.class);
    }
}
