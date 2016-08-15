package org.pyjjs.scheduler.core.microeconomic

import org.junit.Test
import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import java.util.*

import org.hamcrest.MatcherAssert.*
import org.hamcrest.CoreMatchers.*
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange

class SchedulerIsWorking: BaseSchedulerTest() {


    @Test(timeout = 10000)
    fun schedulerIsWorking() {
        val resource = Resource()
        resource.capacity = 1.0

        val task = Task()
        task.deadline = Date()

        val task2 = Task()
        task2.deadline = Date()

        saveEntities(resource, task, task2)
    }

    override fun assertPlan(plan: Plan, lastAppliedChanges: SortedSet<PlanChange>) {
        println("Asserting...")
        assertThat(plan.resourceUsages.size, equalTo(1))
        plan.resourceUsages.forEach { println(it.id) }
        print("---")
        lastAppliedChanges.forEach { println(it.id) }
        println("Plan asserted")
    }

}

