package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage
import org.pyjjs.scheduler.core.common.SystemConfigKeys

class GotNewChangesBehaviour : Behaviour<SchedulingControllerState, PlanUpdatedMessage>() {
    override fun perform(message: PlanUpdatedMessage) {
        actorState.planChanges.addAll(message.planChanges)
        BEHAVIOUR_LOG.info("Actor ${getActorLocalName(message.sender!!)} notify about changes: ${message.planChanges}")
        val placedTasks = message.planChanges.map { it.resourceUsage.task }
        val placedTaskRefs = actorState.tasks.filterKeys { placedTasks.contains(it) }.values
        actorState.unplacedTasks.removeAll(placedTaskRefs)
        BEHAVIOUR_LOG.info("Remained tasks amount: ${actorState.unplacedTasks.size}")
        if(actorState.unplacedTasks.isEmpty()) {
            val checkChangesAreScheduled = actorState.checkOffersAreScheduled()

            if (!checkChangesAreScheduled) {
                scheduleNotification(CheckNewChanges(), SystemConfigKeys.SCHEDULE_CONTROLLER_WAITING_IN_MILLIS_KEY)
                actorState.setCheckOffersAreScheduled(true)
            }
        }
        saveActorState(actorState)
    }

    override fun processMessage(): Class<PlanUpdatedMessage> {
        return PlanUpdatedMessage::class.java
    }
}
