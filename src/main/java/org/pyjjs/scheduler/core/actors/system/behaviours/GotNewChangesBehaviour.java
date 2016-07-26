package org.pyjjs.scheduler.core.actors.system.behaviours;

import org.pyjjs.scheduler.core.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.actors.system.SchedulingControllerState;
import org.pyjjs.scheduler.core.actors.system.messages.PlanUpdatedMessage;

public class GotNewChangesBehaviour extends Behaviour<SchedulingControllerState, PlanUpdatedMessage> {
    @Override
    protected void perform(PlanUpdatedMessage message) {
        SchedulingControllerState actorState = getActorState();
        actorState.getPlanChanges().addAll(message.getPlanChanges());
        saveActorState(actorState);

        //TODO run notify about changes
    }

    @Override
    protected Class<PlanUpdatedMessage> processMessage() {
        return PlanUpdatedMessage.class;
    }
}
