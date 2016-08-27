package org.pyjjs.scheduler.core.init_assertions

import org.junit.Test
import org.pyjjs.scheduler.core.placement.time.Availability

class ResourceAvailabilityInitTests {

    @Test(expected = IllegalStateException::class)
    fun intervalLessThenDurationThrowsException() {
        Availability.Periodic(start = 5, duration = 5, capacity = 1.0, interval = 4, end = 12)
    }
    @Test(expected = IllegalStateException::class)
    fun intervalEqualsDurationThrowsException() {
        Availability.Periodic(start = 5, duration = 5, capacity = 1.0, interval = 5, end = 12)
    }

    @Test(expected = IllegalStateException::class)
    fun startEndDeltaEqualsOneInterval() {
        Availability.Periodic(start = 5, duration = 5, capacity = 1.0, interval = 5, end = 10)
    }

    @Test(expected = IllegalStateException::class)
    fun startEndDeltaLessThanOneInterval() {
        Availability.Periodic(start = 5, duration = 5, capacity = 1.0, interval = 6, end = 10)
    }

    @Test
    fun initIsSuccessful() {
        Availability.Periodic(start = 5, duration = 5, capacity = 1.0, interval = 6, end = 16)
    }

}