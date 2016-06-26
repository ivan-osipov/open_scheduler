package org.pyjjs.scheduler.core.actors.resource.supervisor;

import org.pyjjs.scheduler.core.actors.common.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.resource.supervisor.behaviours.CreateResourceBehaviour;

public class ResourceSupervisor extends BehaviourBasedActor<ResourceSupervisorState> {
    @Override
    protected ResourceSupervisorState getInitialState() {
        return new ResourceSupervisorState(getContext());
    }

    @Override
    protected void init() {
        fillBehaviours();
    }

    private void fillBehaviours() {
        addBehaviour(CreateResourceBehaviour.class);
    }
}
