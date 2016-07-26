package org.pyjjs.scheduler.core.microeconomic

import org.junit.Test
import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import java.util.*

import org.hamcrest.MatcherAssert.*
import org.hamcrest.CoreMatchers.*

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

    override fun assertPlan(plan: Plan) {
        assertThat(plan, equalTo(Any()))
    }

}

