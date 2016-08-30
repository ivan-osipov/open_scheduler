package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import com.google.common.collect.ImmutableSortedSet
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges
import org.pyjjs.scheduler.core.api.impl.utils.Comparators

class NotifyAboutChangesBehaviour: Behaviour<SchedulingControllerState, CheckNewChanges>() {
    override fun perform(message: CheckNewChanges) {
        BEHAVIOUR_LOG.info("Scheduling controlled notify about changes: ${actorState.planChanges}")
        val changes = ImmutableSortedSet.copyOf(Comparators.TIMESTAMP_COMPARATOR, actorState.planChanges)
        actorState.schedulingListeners.forEach { it.onChange(changes) }
        actorState.planChanges.clear()
        actorState.setCheckOffersAreScheduled(false)
        saveActorState(actorState)

        val sumTaskDiscontent = actorState.discontentsByTaskActors.values.sum()
        BEHAVIOUR_LOG.info("\nSystem objective function: $sumTaskDiscontent\n")
        allowMostDiscontentedActorToFindAlternatives(actorState)
    }

    private fun allowMostDiscontentedActorToFindAlternatives(actorState: SchedulingControllerState) {
        if (!actorState.discontentsByTaskActors.isEmpty()) {
            val mostDiscontentedActor = actorState.discontentsByTaskActors.maxBy { it.value }!!.key
            //TODO send allowing message
        }
    }

    override fun processMessage(): Class<CheckNewChanges> {
        return CheckNewChanges::class.java
    }

}