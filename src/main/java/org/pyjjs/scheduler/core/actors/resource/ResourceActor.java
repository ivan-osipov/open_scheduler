package org.pyjjs.scheduler.core.actors.resource;

import org.pyjjs.scheduler.core.actors.common.*;
import org.pyjjs.scheduler.core.actors.resource.behaviours.FindPlacementBehaviour;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;

public class ResourceActor extends BehaviourBasedActor<ResourceActorState> implements HasObjectiveFunction {

    @Override
    protected ResourceActorState getInitialState() {
        return new ResourceActorState(getSelf());
    }

    @Override
    public double calculateObjectiveFunction() {
        return 0;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        fillBehaviours();
    }

    private void fillBehaviours() {
        addBehaviour(FindPlacementBehaviour.get());
    }
}
