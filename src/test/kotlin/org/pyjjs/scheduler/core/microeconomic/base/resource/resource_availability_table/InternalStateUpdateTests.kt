package org.pyjjs.scheduler.core.microeconomic.base.resource.resource_availability_table

import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.pyjjs.scheduler.core.ResourceAvailability
import org.pyjjs.scheduler.core.model.ResourceAvailabilityTable

class InternalStateUpdateTests {
    @Test
    fun resourceAvailabilityTableUpdateMinCapacity() {
        val resourceAvailabilityTable = ResourceAvailabilityTable()
        assertThat(resourceAvailabilityTable.minAvailableCapacity, nullValue())
        resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(0, 10, capacity = 2.0))
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(2.0))
        resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(0, 10, capacity = 4.0))
        val availability = ResourceAvailability(15, 25, capacity = 1.0)
        resourceAvailabilityTable.addResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(1.0))
        resourceAvailabilityTable.removeResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(2.0))
        resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(30, 45, capacity = 1.5))
        assertThat(resourceAvailabilityTable.minAvailableCapacity, equalTo(1.5))
        resourceAvailabilityTable.clearAvailabilities()
        assertThat(resourceAvailabilityTable.minAvailableCapacity, nullValue())
    }

    @Test
    fun resourceAvailabilityTableUpdateMaxCapacity() {
        val resourceAvailabilityTable = ResourceAvailabilityTable()
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, nullValue())
        val availability = ResourceAvailability(0, 10, capacity = 2.0)
        resourceAvailabilityTable.addResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(2.0))
        val availability1 = ResourceAvailability(15, 25, capacity = 1.0)
        resourceAvailabilityTable.addResourceAvailability(availability1)
        val availability2 = ResourceAvailability(15, 25, capacity = 1.5)
        resourceAvailabilityTable.addResourceAvailability(availability2)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(2.0))
        resourceAvailabilityTable.removeResourceAvailability(availability)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(1.5))
        resourceAvailabilityTable.removeResourceAvailability(availability2)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(1.0))
        resourceAvailabilityTable.removeResourceAvailability(availability1)
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, nullValue())
        resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(30, 45, capacity = 3.0))
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, equalTo(3.0))
        resourceAvailabilityTable.clearAvailabilities()
        assertThat(resourceAvailabilityTable.maxAvailableCapacity, nullValue())
    }
}