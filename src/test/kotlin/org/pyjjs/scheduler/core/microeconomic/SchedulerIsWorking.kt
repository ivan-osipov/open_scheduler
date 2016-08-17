package org.pyjjs.scheduler.core.microeconomic

import org.junit.Test
import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import java.util.*

import org.hamcrest.MatcherAssert.*
import org.hamcrest.CoreMatchers.*
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.model.ResourceCriteria
import org.pyjjs.scheduler.core.model.criteria.StrictCriterion
import org.pyjjs.scheduler.core.model.criteria.operations.bool.Equals

class SchedulerIsWorking: BaseSchedulerTest() {


    @Test
    fun schedulerIsWorking() {
        val resource = Resource()
        resource.capacity = 1.0

        val task = Task()
        task.deadline = Date()
        task.resourceCriteria = ResourceCriteria()
        task.resourceCriteria.strictCriterion.add(StrictCriterion(2.0,"capacity", Equals()))

        val task2 = Task()
        task2.deadline = Date()
        task2.resourceCriteria.strictCriterion.add(StrictCriterion(1.0,"capacity", Equals()))

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

