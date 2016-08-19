package org.pyjjs.scheduler.core.api.impl.actors.system

import akka.actor.ActorContext
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.api.SchedulingListener
import org.pyjjs.scheduler.core.api.impl.utils.Comparators
import java.util.SortedSet
import java.util.TreeSet

class SchedulingControllerState(actorContext: ActorContext) : ActorState(actorContext) {

    var schedulingListeners: Set<SchedulingListener>? = null

    var planChanges: SortedSet<PlanChange> = TreeSet(Comparators.TIMESTAMP_COMPARATOR)

    private var checkOffersAreScheduled = false

    override fun copySelf() = this

    fun checkOffersAreScheduled(): Boolean {
        return checkOffersAreScheduled
    }

    fun setCheckOffersAreScheduled(checkOffersAreScheduled: Boolean) {
        this.checkOffersAreScheduled = checkOffersAreScheduled
    }
}
