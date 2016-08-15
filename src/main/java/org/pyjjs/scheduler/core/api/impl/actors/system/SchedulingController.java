package org.pyjjs.scheduler.core.api.impl.actors.system;

import com.google.common.collect.Sets;
import org.pyjjs.scheduler.core.api.SchedulingListener;
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.GotNewChangesBehaviour;
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.NotifyAboutChangesBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulingController extends BehaviourBasedActor<SchedulingControllerState> {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulingController.class);

    public SchedulingController(SchedulingListener listeners) {
        SchedulingControllerState state = getCopyOfActorState();
        state.setSchedulingListeners(Sets.newHashSet(listeners));
        updateActorState(state);
    }

    @Override
    protected SchedulingControllerState getInitialState() {
        return new SchedulingControllerState(getContext());
    }

    @Override
    protected void init() {
        fillBehaviours();
        LOG.info("Scheduling controller is initialized");
    }

    private void fillBehaviours() {
        addBehaviour(GotNewChangesBehaviour.class);
        addBehaviour(NotifyAboutChangesBehaviour.class);
    }
}
