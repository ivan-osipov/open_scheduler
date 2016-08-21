package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.placement.time.ResourceAvailability
import org.pyjjs.scheduler.core.api.impl.utils.Comparators
import java.util.*
import org.pyjjs.scheduler.core.nullsafety.*

class ResourceAvailabilityTable {
    var resourceAvailabilities: MutableSet<ResourceAvailability> = TreeSet(Comparators.RESOURCE_AVAILABILITY_COMPARATOR)
        private set

    var minAvailableCapacity: Double? = null
        private set

    var maxAvailableCapacity: Double? = null
        private set

    fun addResourceAvailability(availability: ResourceAvailability) {
        resourceAvailabilities.add(availability)
        minAvailableCapacity = min(minAvailableCapacity, availability.capacity)
        maxAvailableCapacity = max(maxAvailableCapacity, availability.capacity)
    }

    fun removeResourceAvailability(availability: ResourceAvailability) {
        resourceAvailabilities.remove(availability)
        minAvailableCapacity = null
        maxAvailableCapacity = null
        if(!resourceAvailabilities.isEmpty()) {
            val availabilities = resourceAvailabilities.sortedBy { it.capacity }
            minAvailableCapacity = availabilities.first().capacity
            maxAvailableCapacity = availabilities.last().capacity
        }
    }

    fun clearAvailabilities() {
        resourceAvailabilities.clear()
        minAvailableCapacity = null
        maxAvailableCapacity = null
    }

    fun isEmpty() = resourceAvailabilities.isEmpty()
}