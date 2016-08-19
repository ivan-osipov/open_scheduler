package org.pyjjs.scheduler.core.microeconomic

import org.junit.Test
import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import java.util.*

import org.hamcrest.MatcherAssert.*
import org.hamcrest.CoreMatchers.*
import org.pyjjs.scheduler.core.model.ResourceAvailability
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.model.ResourceCriteria
import org.pyjjs.scheduler.core.model.criteria.StrictCriterion
import org.pyjjs.scheduler.core.model.criteria.operations.bool.Equals

class SchedulerIsWorking: BaseSchedulerTest() {


    @Test
    fun schedulerIsWorking() {
        val resource = Resource()
        resource.resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(0, 10, capacity = 2.0))
        resource.resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(15, 25, capacity = 1.0))

        val task = Task()
        task.deadline = Date()
        task.resourceCriteria = ResourceCriteria()
        task.resourceCriteria.strictCriterion.add(StrictCriterion("r.resourceAvailabilityTable.minAvailableCapacity", Equals(), 2.0))

        val task2 = Task()
        task2.deadline = Date()
        task2.resourceCriteria.strictCriterion.add(StrictCriterion("r.resourceAvailabilityTable.minAvailableCapacity", Equals(), 1.0))

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

