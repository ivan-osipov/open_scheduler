package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import com.google.common.collect.ImmutableSortedSet
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges
import org.pyjjs.scheduler.core.api.impl.utils.Comparators

class NotifyAboutChangesBehaviour: Behaviour<SchedulingControllerState, CheckNewChanges>() {
    override fun perform(message: CheckNewChanges?) {
        val detachedActorState = actorState

        val changes = ImmutableSortedSet.copyOf(Comparators.TIMESTAMP_COMPARATOR, detachedActorState.planChanges)
        detachedActorState.schedulingListeners.forEach { it.onChange(changes) }
        detachedActorState.planChanges.clear()
        detachedActorState.setCheckOffersAreScheduled(false)

        saveActorState(detachedActorState)
    }

    override fun processMessage(): Class<CheckNewChanges> {
        return CheckNewChanges::class.java
    }

}