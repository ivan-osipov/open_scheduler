package org.pyjjs.scheduler.core.init_assertions

import org.junit.Test
import org.pyjjs.scheduler.core.placement.time.PeriodicResourceAvailability

class ResourceAvailabilityInitTests {

    @Test(expected = IllegalStateException::class)
    fun intervalLessThenDurationThrowsException() {
        PeriodicResourceAvailability(start = 5, duration = 5, capacity = 1.0, interval = 4, end = 12)
    }
    @Test(expected = IllegalStateException::class)
    fun intervalEqualsDurationThrowsException() {
        PeriodicResourceAvailability(start = 5, duration = 5, capacity = 1.0, interval = 5, end = 12)
    }

    @Test(expected = IllegalStateException::class)
    fun startEndDeltaEqualsOneInterval() {
        PeriodicResourceAvailability(start = 5, duration = 5, capacity = 1.0, interval = 5, end = 10)
    }

    @Test(expected = IllegalStateException::class)
    fun startEndDeltaLessThanOneInterval() {
        PeriodicResourceAvailability(start = 5, duration = 5, capacity = 1.0, interval = 6, end = 10)
    }

    @Test
    fun initIsSuccessful() {
        PeriodicResourceAvailability(start = 5, duration = 5, capacity = 1.0, interval = 6, end = 16)
    }

}