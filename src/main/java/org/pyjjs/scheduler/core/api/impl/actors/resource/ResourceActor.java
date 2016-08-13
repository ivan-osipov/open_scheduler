package org.pyjjs.scheduler.core.api.impl.actors.resource;

import org.pyjjs.scheduler.core.api.impl.actors.common.*;
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours.FindPlacementBehaviour;
import org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours.ResourceInitBehaviour;

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
        addBehaviour(ResourceInitBehaviour.class);
        addBehaviour(FindPlacementBehaviour.class);
    }
}
