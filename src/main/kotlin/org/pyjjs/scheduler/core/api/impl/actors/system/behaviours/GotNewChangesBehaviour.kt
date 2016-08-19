package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage
import org.pyjjs.scheduler.core.common.SystemConfigKeys

class GotNewChangesBehaviour : Behaviour<SchedulingControllerState, PlanUpdatedMessage>() {
    override fun perform(message: PlanUpdatedMessage) {
        val actorState = actorState
        actorState.planChanges.addAll(message.planChanges)
        val checkChangesAreScheduled = actorState.checkOffersAreScheduled()

        if (!checkChangesAreScheduled) {
            scheduleNotification(CheckNewChanges(), SystemConfigKeys.SCHEDULE_CONTROLLER_WAITING_IN_MILLIS_KEY)
            actorState.setCheckOffersAreScheduled(true)
        }
        saveActorState(actorState)
    }

    override fun processMessage(): Class<PlanUpdatedMessage> {
        return PlanUpdatedMessage::class.java
    }
}
