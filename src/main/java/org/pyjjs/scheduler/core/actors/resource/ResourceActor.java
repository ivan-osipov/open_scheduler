package org.pyjjs.scheduler.core.actors.resource;

import org.pyjjs.scheduler.core.actors.common.*;
import org.pyjjs.scheduler.core.actors.resource.behaviours.FindPlacementBehaviour;
import org.pyjjs.scheduler.core.actors.resource.behaviours.ResourceInitBehaviour;
import org.pyjjs.scheduler.core.actors.task.behaviours.TaskInitBehaviour;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;

public class ResourceActor extends BehaviourBasedActor<ResourceActorState> implements HasObjectiveFunction {

    @Override
    protected ResourceActorState getInitialState() {
        return new ResourceActorState(getContext());
    }

    @Override
    public double calculateObjectiveFunction() {
        return 0;
    }

    @Override
    protected void init() {
        fillBehaviours();
    }

    private void fillBehaviours() {
        addBehaviour(ResourceInitBehaviour.get());
        addBehaviour(FindPlacementBehaviour.get());
    }
}
