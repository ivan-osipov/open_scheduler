package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage;

public class GotNewChangesBehaviour extends Behaviour<SchedulingControllerState, PlanUpdatedMessage> {
    @Override
    protected void perform(PlanUpdatedMessage message) {
        SchedulingControllerState actorState = getActorState();
        actorState.getPlanChanges().addAll(message.getPlanChanges());
        saveActorState(actorState);

        actorState.getSchedulingListeners().forEach(listener -> listener.onChange(message.getPlanChanges()));
    }

    @Override
    protected Class<PlanUpdatedMessage> processMessage() {
        return PlanUpdatedMessage.class;
    }
}
