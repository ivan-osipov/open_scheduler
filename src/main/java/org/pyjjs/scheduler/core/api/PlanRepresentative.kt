package org.pyjjs.scheduler.core.api

import java.util.*

interface PlanRepresentative {
    fun addStablePlanListener(listener: StablePlanListener)
    fun removeStablePlanListener(listener: StablePlanListener)

    interface StablePlanListener {
        fun beforePlanUpdate(oldPlan: Plan, upcomingChanges: SortedSet<PlanChange>) {}
        fun afterPlanUpdate(updatedPlan: Plan, lastAppliedChanges: SortedSet<PlanChange>) {}
    }
}

