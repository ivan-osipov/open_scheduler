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
        resource.id = UUID.randomUUID()
        resource.capacity = 1.0

        val task = Task()
        task.id = UUID.randomUUID()
        task.deadline = Date()

        saveEntities(resource, task)
    }

    override fun assertPlan(plan: Plan, lastAppliedChanges: SortedSet<PlanChange>) {
        println("Asserting...")
        assertThat(lastAppliedChanges.size, equalTo(1))
        println("Plan asserted")
    }

}

