package org.pyjjs.scheduler.core.api

import java.util.SortedSet
import java.util.TreeSet

interface SchedulingListener {

    fun onChange(changes: SortedSet<PlanChange>)

    fun onPrimaryPlanUpdate(primaryPlan: Plan)

}
