package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.api.impl.changes.PlanChange.Type.*
import org.pyjjs.scheduler.core.api.impl.changes.InsertCommitter
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.api.impl.changes.RemoveCommitter
import org.pyjjs.scheduler.core.api.impl.changes.UpdateCommitter
import org.pyjjs.scheduler.core.api.impl.utils.Comparators
import org.pyjjs.scheduler.core.model.ResourceUsage
import java.util.*

class PlanMergingController : SchedulingListener, PlanRepresentative {
    private var plan: Plan = PlanImpl()
    private val INSERT_COMMITTER = InsertCommitter()
    private val UPDATE_COMMITTER = UpdateCommitter()
    private val REMOVE_COMMITTER = RemoveCommitter()
    private val stablePlanListeners: MutableCollection<PlanRepresentative.StablePlanListener> = HashSet()

    override fun addStablePlanListener(listener: PlanRepresentative.StablePlanListener) {
        stablePlanListeners.add(listener)
    }

    override fun removeStablePlanListener(listener: PlanRepresentative.StablePlanListener) {
        stablePlanListeners.remove(listener)
    }

    override fun onChange(changes: SortedSet<PlanChange>) {
        notifyListenersBeforeUpdate(changes)

        changes.forEach {
            val changeCommitter = when (it.type) {
                INSERT -> INSERT_COMMITTER
                UPDATE -> UPDATE_COMMITTER
                REMOVE -> REMOVE_COMMITTER
            }
            val updatedPlan: Plan = changeCommitter.commit(plan, it)
            refreshPlan(updatedPlan)
        }

        notifyListenersAfterUpdate(changes)
    }

    private fun refreshPlan(updatedPlan: Plan) {
        plan = updatedPlan
    }

    override fun onPrimaryPlanUpdate(primaryPlan: Plan) {
        plan = primaryPlan
    }

    private fun notifyListenersBeforeUpdate(upcomingChanges: SortedSet<PlanChange>) {
        stablePlanListeners.forEach { it.beforePlanUpdate(plan, upcomingChanges) }
    }


    private fun notifyListenersAfterUpdate(appliedChanges: SortedSet<PlanChange>) {
        stablePlanListeners.forEach { it.afterPlanUpdate(plan, appliedChanges) }
    }

    private inner class PlanImpl : Plan {
        override var lastUpdate: Long = 0

        override var version: Long = 0

        override var differentWithIdeal: Double = Double.POSITIVE_INFINITY

        override val resourceUsages = TreeSet<ResourceUsage> (Comparators.RESOURCE_USAGE_COMPARATOR)

        override fun toString(): String{
            return "PlanImpl(lastUpdate=$lastUpdate, version=$version, differentWithIdeal=$differentWithIdeal, resourceUsages=$resourceUsages)"
        }


    }
}
