package org.pyjjs.scheduler.core.microeconomic.base.resource.resource_availability_table

import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNull
import org.junit.Assert.assertThat
import org.junit.Test
import org.pyjjs.scheduler.core.ResourceAvailability
import org.pyjjs.scheduler.core.model.Resource

class InternalStateUpdateTests {
    @Test
    fun resourceAvailabilityTableUpdateMinCapacity() {
        val resource = Resource()
        val resourceAvailabilityTable = resource.resourceAvailabilityTable
        assertThat(resourceAvailabilityTable.minAvailableCapacity, IsNull())
        resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(0, 10, capacity = 2.0))
        assertThat(resourceAvailabilityTable.minAvailableCapacity, IsEqual(2.0))
        resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(15, 25, capacity = 1.0))
        assertThat(resourceAvailabilityTable.minAvailableCapacity, IsEqual(1.0))
        resourceAvailabilityTable.addResourceAvailability(ResourceAvailability(30, 45, capacity = 3.0))
        assertThat(resourceAvailabilityTable.minAvailableCapacity, IsEqual(1.0))
        resourceAvailabilityTable.clearAvailabilities()
        assertThat(resourceAvailabilityTable.minAvailableCapacity, IsNull())
    }
}