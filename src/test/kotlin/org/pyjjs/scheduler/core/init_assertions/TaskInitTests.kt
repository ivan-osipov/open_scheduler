package org.pyjjs.scheduler.core.init_assertions

import org.junit.Test
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import org.pyjjs.scheduler.core.model.Task

class TaskInitTests {

    @Test
    fun minValuesEqualLimitsThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 4.0,
                maxCapacity = 5.0,
                minDuration = 3,
                maxDuration = 5))
    }
    @Test
    fun maxValuesEqualLimitsThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 3.0,
                maxCapacity = 4.0,
                minDuration = 2,
                maxDuration = 3))
    }

    @Test(expected = IllegalStateException::class)
    fun overLimitedMinValuesThrowsException() {
        Task(TaskDescriptor(
                laborContent = 10.0,
                minCapacity = 4.0,
                maxCapacity = 5.0,
                minDuration = 3,
                maxDuration = 5))
    }

    @Test(expected = IllegalStateException::class)
    fun lessLimitedMaxValuesThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 3.0,
                maxCapacity = 5.0,
                minDuration = 1,
                maxDuration = 2))
    }

    @Test(expected = IllegalStateException::class)
    fun minDurationGreaterThanMaxDurationThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 5.0,
                maxCapacity = 4.0,
                minDuration = 1,
                maxDuration = 2))
    }

    @Test(expected = IllegalStateException::class)
    fun minDurationEqualsMaxDurationThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 4.0,
                maxCapacity = 4.0,
                minDuration = 1,
                maxDuration = 2))
    }

    @Test(expected = IllegalStateException::class)
    fun minCapacityGreaterThanMaxCapacityThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 3.0,
                maxCapacity = 4.0,
                minDuration = 3,
                maxDuration = 2))
    }

    @Test(expected = IllegalStateException::class)
    fun minCapacityEqualsMaxCapacityThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 3.0,
                maxCapacity = 4.0,
                minDuration = 2,
                maxDuration = 2))
    }

    @Test
    fun successfulInitThrowsException() {
        Task(TaskDescriptor(
                laborContent = 12.0,
                minCapacity = 3.0,
                maxCapacity = 4.0,
                minDuration = 2,
                maxDuration = 3))
    }

}