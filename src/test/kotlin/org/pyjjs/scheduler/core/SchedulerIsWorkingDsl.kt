package org.pyjjs.scheduler.core

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.*
import org.junit.Test
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import org.pyjjs.scheduler.core.dsl.BaseSchedulerDslTest
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import org.pyjjs.scheduler.core.model.criteria.StrictCriterion
import org.pyjjs.scheduler.core.model.criteria.operations.bool.LessThan
import org.pyjjs.scheduler.core.placement.time.Availability

class SchedulerIsWorkingDsl: BaseSchedulerDslTest() {

    @Test
    fun schedulerIsWorking() {
        dataSource {
            val resource = Resource().apply {
                timeSheet.addResourceAvailability(Availability.Occasional(0, 6, capacity = 2.0))
                timeSheet.addResourceAvailability(Availability.Occasional(15, 9, capacity = 1.0))
            }

            val task = Task(TaskDescriptor(
                    laborContent = 10.0,
                    minCapacity = 1.0,
                    maxCapacity = 3.0,
                    minDuration = 1,
                    maxDuration = 5)).apply {
                resourceCriteria.strictCriterion.add(StrictCriterion("timeSheet.resourceAvailabilityTable.minAvailableCapacity", LessThan(), 2.0))
            }
            setOf(resource, task)
        }
        assertSchedule { plan, changes ->
            assertThat(plan.resourceUsages.size, equalTo(1))
        }
    }

}