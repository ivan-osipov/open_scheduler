package org.pyjjs.scheduler.core.base.resource.resource_availability_table

import org.junit.Test
import org.pyjjs.scheduler.core.placement.time.TimeSheet
import org.junit.Assert.assertThat
import org.hamcrest.Matchers.*
import org.pyjjs.scheduler.core.placement.time.Availability

class PeriodAvailabilityTests {
    @Test
    fun addOnePeriodAvailabilityGenerateManyTimeParts() {
        val ts = TimeSheet()
        ts.addResourceAvailability(Availability.Periodic(start = 5, duration = 5, capacity = 1.0, interval = 6, end = 15))

        assertThat(ts.freeTimes.size, equalTo(2))
        val iterator = ts.freeTimes.iterator()
        val first = iterator.next()
        val second = iterator.next()
        assertThat(first.start, equalTo(5L))
        assertThat(first.duration, equalTo(5L))

        assertThat(second.start, equalTo(11L))
        assertThat(second.duration, equalTo(4L))
    }
}
