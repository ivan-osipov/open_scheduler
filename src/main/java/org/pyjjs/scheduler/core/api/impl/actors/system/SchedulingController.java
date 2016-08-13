package org.pyjjs.scheduler.core.api.impl.actors.system;

import com.google.common.collect.Sets;
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor;
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.GotNewChangesBehaviour;
import org.pyjjs.scheduler.core.api.SchedulingListener;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage;
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange;
import org.pyjjs.scheduler.core.api.impl.utils.Comparators;
import org.pyjjs.scheduler.core.model.Resource;
import org.pyjjs.scheduler.core.model.ResourceUsage;
import org.pyjjs.scheduler.core.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.TreeSet;

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
        PlanUpdatedMessage message = new PlanUpdatedMessage();
        message.getPlanChanges().add(PlanChange.Companion.insert().task(new Task()).start(new Date()).end(new Date()).resource(new Resource()));
        getSelf().tell(message, getSelf());
    }

    private void fillBehaviours() {
        addBehaviour(GotNewChangesBehaviour.class);
    }
}
