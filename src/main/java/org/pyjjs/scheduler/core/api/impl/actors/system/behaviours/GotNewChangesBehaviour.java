package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage;
import org.pyjjs.scheduler.core.common.SystemConfigKeys;

public class GotNewChangesBehaviour extends Behaviour<SchedulingControllerState, PlanUpdatedMessage> {
    @Override
    protected void perform(PlanUpdatedMessage message) {
        SchedulingControllerState actorState = getActorState();
        actorState.getPlanChanges().addAll(message.getPlanChanges());
        boolean checkChangesAreScheduled = actorState.checkOffersAreScheduled();

        if(!checkChangesAreScheduled) {
            scheduleNotification(new CheckNewChanges(), SystemConfigKeys.SCHEDULE_CONTROLLER_WAITING_IN_MILLIS_KEY);
            actorState.setCheckOffersAreScheduled(true);
        }
        saveActorState(actorState);
    }

    @Override
    protected Class<PlanUpdatedMessage> processMessage() {
        return PlanUpdatedMessage.class;
    }
}
