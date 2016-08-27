package org.pyjjs.scheduler.core

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import org.pyjjs.scheduler.core.model.criteria.StrictCriterion
import org.pyjjs.scheduler.core.model.criteria.operations.bool.Equals
import org.pyjjs.scheduler.core.model.criteria.operations.bool.LessThan
import org.pyjjs.scheduler.core.placement.time.Availability
import java.util.*
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor as TD

class SchedulerIsWorking: BaseSchedulerTest() {


    @Test
    fun schedulerIsWorking() {
        val resource = Resource()
        resource.timeSheet.addResourceAvailability(Availability.Occasional(0, 6, capacity = 2.0))
        resource.timeSheet.addResourceAvailability(Availability.Occasional(15, 9, capacity = 1.0))

        val task = Task(TD(
                laborContent = 10.0,
                minCapacity = 1.0,
                maxCapacity = 3.0,
                minDuration = 1,
                maxDuration = 5,
                deadline = 10))
        task.resourceCriteria.strictCriterion.add(StrictCriterion("timeSheet.resourceAvailabilityTable.minAvailableCapacity", LessThan(), 2.0))

        val task2 = Task(TD(
                laborContent = 10.0,
                minCapacity = 1.0,
                maxCapacity = 3.0,
                minDuration = 1,
                maxDuration = 5,
                deadline = 20))
        task2.resourceCriteria.strictCriterion.add(StrictCriterion("timeSheet.resourceAvailabilityTable.minAvailableCapacity", Equals(), 1.0))

        saveEntities(resource, task, task2)
    }

    override fun assertPlan(plan: Plan, lastAppliedChanges: SortedSet<PlanChange>) {
        println("Asserting...")
        assertThat(plan.resourceUsages.size, equalTo(2))
        plan.resourceUsages.forEach { println(it.id) }
        println("Plan version: ${plan.version}")
        println("Plan asserted")
    }

}

