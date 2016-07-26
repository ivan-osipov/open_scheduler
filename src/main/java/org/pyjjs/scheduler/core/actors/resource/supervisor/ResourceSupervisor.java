package org.pyjjs.scheduler.core.actors.resource.supervisor;

import org.pyjjs.scheduler.core.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.actors.resource.supervisor.behaviours.CreateResourceBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceSupervisor extends BehaviourBasedActor<ResourceSupervisorState> {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceSupervisor.class);

    @Override
    protected ResourceSupervisorState getInitialState() {
        return new ResourceSupervisorState(getContext());
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
