package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.api.impl.utils.Comparators
import java.util.*

class TimeSheet {

    var planningHorizon = java.lang.Long.MAX_VALUE

    internal val resourceAvailabilityTable = ResourceAvailabilityTable()
    val freeTimes = TreeSet<TimePart>(Comparators.TIME_PART_COMPARATOR)
    val usedTimes = TreeSet<UsedTime>(Comparators.USED_TIME_COMPARATOR)

    var freeLaborContent: Double = 0.0
        private set

    fun findNearestFreeTime(): TimePart? {
        val freeTimeIterator = freeTimes.iterator()
        if (freeTimeIterator.hasNext()) {
            return freeTimeIterator.next()
        }
        return null
    }

    fun findNearestUsedTime(): UsedTime? {
        val usedTimeIterator = usedTimes.iterator()
        if (usedTimeIterator.hasNext()) {
            return usedTimeIterator.next()
        }
        return null
    }

    fun addResourceAvailability(availability: ResourceAvailability) {
        resourceAvailabilityTable.addResourceAvailability(availability)
        if(availability is PeriodicResourceAvailability) {
            generateTimePartsByPeriodicAvailability(availability)
        } else {
            val timePart = TimePart(availability.start, availability.duration, availability.capacity)
            freeTimes.add(timePart)
            freeLaborContent += timePart.laborContent
        }
    }

    private fun generateTimePartsByPeriodicAvailability(availability: PeriodicResourceAvailability) {
        var start = availability.start
        do {
            val remainedTime = availability.end - start
            val timePart = TimePart(start,
                    duration = Math.min(remainedTime, availability.duration),
                    capacity = availability.capacity)

            freeTimes.add(timePart)
            freeLaborContent += timePart.laborContent
            start += availability.interval
        } while (start < availability.end)
    }

    fun removeResourceAvailability(availability: ResourceAvailability) {
        resourceAvailabilityTable.removeResourceAvailability(availability)

        //todo remove on change
    }

}