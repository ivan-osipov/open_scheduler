package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges;
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage;
import org.pyjjs.scheduler.core.api.impl.actors.task.messages.CheckOffersMessage;

public class GotNewChangesBehaviour extends Behaviour<SchedulingControllerState, PlanUpdatedMessage> {
    @Override
    protected void perform(PlanUpdatedMessage message) {
        SchedulingControllerState actorState = getActorState();
        actorState.getPlanChanges().addAll(message.getPlanChanges());
        boolean checkChangesAreScheduled = actorState.checkOffersAreScheduled();

        if(!checkChangesAreScheduled) {
            scheduleNotification(new CheckNewChanges());
            actorState.setCheckOffersAreScheduled(true);
        }
        saveActorState(actorState);
    }

    @Override
    protected Class<PlanUpdatedMessage> processMessage() {
        return PlanUpdatedMessage.class;
    }
}
