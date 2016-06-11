package org.pyjjs.scheduler.core.actors.task;

import akka.actor.ActorSelection;
import org.pyjjs.scheduler.core.actors.common.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.resource.messages.FoundResourceMessage;
import org.pyjjs.scheduler.core.actors.task.behaviours.FoundResourceBehaviour;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;

public class TaskActor extends BehaviourBasedActor<TaskActorState> {

    @Override
    protected TaskActorState getInitialState() {
        return new TaskActorState(getSelf());
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        fillBehaviours();
        LOG.info("Task Actor started: " + this.getSelf().path().toString());
        ActorSelection resourceSelection = getContext().actorSelection("../*");
        resourceSelection.tell(new IFindSomeResourceMessage(getSelf()), getSelf());
    }

    private void fillBehaviours() {
        addBehaviour(FoundResourceBehaviour.get());
    }
}
