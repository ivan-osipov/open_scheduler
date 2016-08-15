package org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.behaviours.CreateResourceBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceSupervisor extends BehaviourBasedActor<ResourceSupervisorState> {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceSupervisor.class);

    @Override
    protected ResourceSupervisorState getInitialState() {
        ResourceSupervisorState resourceSupervisorState = new ResourceSupervisorState(getContext());
        resourceSupervisorState.setInitialized(true);
        return resourceSupervisorState;
    }

    @Override
    protected void init() {
        fillBehaviours();
        LOG.info("Resource Supervisor initialized");
    }

    private void fillBehaviours() {
        addBehaviour(CreateResourceBehaviour.class);
    }
}
