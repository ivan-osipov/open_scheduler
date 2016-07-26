package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.api.PlanChange.ChangeType.*
import org.pyjjs.scheduler.core.api.internal.InsertCommitter
import org.pyjjs.scheduler.core.api.internal.RemoveCommitter
import org.pyjjs.scheduler.core.api.internal.UpdateCommitter
import org.pyjjs.scheduler.core.model.ResourceUsage
import java.util.*

class PlanMergingController : SchedulingListener, PlanRepresentative {
    private var plan: Plan = PlanImpl()
    private val stablePlanListeners: MutableCollection<PlanRepresentative.StablePlanListener> = HashSet();

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
                INSERT -> InsertCommitter()
                UPDATE -> UpdateCommitter()
                REMOVE -> RemoveCommitter()
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

        override val resourceUsages = TreeSet<ResourceUsage> { ru1, ru2 ->
            if(Objects.equals(ru1.id, ru2.id)) return@TreeSet 0;
            ru1.dateRange.start.compareTo(ru2.dateRange.start)
        }
    }
}
