package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.api.impl.utils.Comparators
import java.util.*
import org.pyjjs.scheduler.core.nullsafety.*

class ResourceAvailabilityTable {
    var resourceAvailabilities: MutableSet<Availability> = TreeSet(Comparators.RESOURCE_AVAILABILITY_COMPARATOR)
        private set

    var minAvailableCapacity: Double? = null
        private set

    var maxAvailableCapacity: Double? = null
        private set

    var leftoverInfinityAvailability: Availability.Infinity? = null;

    fun addResourceAvailability(availability: Availability) {
        resourceAvailabilities.add(availability)
        if (availability is Availability.Infinity) {
            leftoverInfinityAvailability = availability
        }
        minAvailableCapacity = min(minAvailableCapacity, availability.capacity)
        maxAvailableCapacity = max(maxAvailableCapacity, availability.capacity)
    }

    fun removeResourceAvailability(availability: Availability) {
        resourceAvailabilities.remove(availability)
        if (availability is Availability.Infinity) {
            leftoverInfinityAvailability = null
        }
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