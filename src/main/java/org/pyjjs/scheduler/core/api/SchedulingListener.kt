package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import java.util.SortedSet
import java.util.TreeSet

interface SchedulingListener {

    fun onChange(changes: SortedSet<PlanChange>)

    fun onPrimaryPlanUpdate(primaryPlan: Plan)

}
