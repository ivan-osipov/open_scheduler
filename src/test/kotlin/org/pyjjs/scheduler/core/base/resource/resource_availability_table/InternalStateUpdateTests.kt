package org.pyjjs.scheduler.core.base.resource.resource_availability_table

import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.pyjjs.scheduler.core.placement.time.Availability
import org.pyjjs.scheduler.core.placement.time.ResourceAvailabilityTable

class InternalStateUpdateTests {
    @Test
    fun resourceAvailabilityTableUpdateMinCapacity() {
        val resourceAvailabilityTable = ResourceAvailabilityTable()
        assertThat(resourceAvailabilityTable.minAvailableCapacity, nullValue())
        resourceAvailabilityTable.addResourceAvailability(Availability.Occasional(0, 10, capacity = 2.0))
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(2.0))
        resourceAvailabilityTable.addResourceAvailability(Availability.Occasional(0, 10, capacity = 4.0))
        val availability = Availability.Occasional(15, 25, capacity = 1.0)
        resourceAvailabilityTable.addResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(1.0))
        resourceAvailabilityTable.removeResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(2.0))
        resourceAvailabilityTable.addResourceAvailability(Availability.Occasional(30, 45, capacity = 1.5))
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(1.5))
        resourceAvailabilityTable.clearAvailabilities()
        assertThat(resourceAvailabilityTable.minAvailableCapacity, nullValue())
    }

    @Test
    fun resourceAvailabilityTableUpdateMaxCapacity() {
        val resourceAvailabilityTable = ResourceAvailabilityTable()
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, nullValue())
        val availability = Availability.Occasional(0, 10, capacity = 2.0)
        resourceAvailabilityTable.addResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(2.0))
        val availability1 = Availability.Occasional(15, 25, capacity = 1.0)
        resourceAvailabilityTable.addResourceAvailability(availability1)
        val availability2 = Availability.Occasional(15, 25, capacity = 1.5)
        resourceAvailabilityTable.addResourceAvailability(availability2)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(2.0))
        resourceAvailabilityTable.removeResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(1.5))
        resourceAvailabilityTable.removeResourceAvailability(availability2)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(1.0))
        resourceAvailabilityTable.removeResourceAvailability(availability1)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, nullValue())
        resourceAvailabilityTable.addResourceAvailability(Availability.Occasional(30, 45, capacity = 3.0))
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(3.0))
        resourceAvailabilityTable.clearAvailabilities()
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, nullValue())
    }
}