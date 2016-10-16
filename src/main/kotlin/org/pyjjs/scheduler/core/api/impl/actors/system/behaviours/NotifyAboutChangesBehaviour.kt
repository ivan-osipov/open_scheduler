package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import com.google.common.collect.ImmutableSortedSet
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.FindBetterMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges
import org.pyjjs.scheduler.core.api.impl.utils.*
import kotlin.reflect.KClass

class NotifyAboutChangesBehaviour: Behaviour<SchedulingControllerState, CheckNewChanges>() {
    override fun perform(message: CheckNewChanges) {
        BEHAVIOUR_LOG.info("Scheduling controlled notify about changes: ${actorState.planChanges}")
        val changes = ImmutableSortedSet.copyOf(TIMESTAMP_COMPARATOR, actorState.planChanges)
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
            val mostDiscontentedActor = actorState.discontentsByTaskActors.minBy { it.value }!!.key
            send(mostDiscontentedActor, FindBetterMessage(actorRef))
        }
    }

    override fun processMessage(): KClass<CheckNewChanges> {
        return CheckNewChanges::class
    }

}