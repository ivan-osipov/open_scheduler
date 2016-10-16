package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.api.impl.utils.*
import java.util.*

class TimeSheet(
        var resourceAvailabilityTable: ResourceAvailabilityTable = ResourceAvailabilityTable(),
        var freeTimes: TreeSet<TimePart> = TreeSet(TIME_PART_COMPARATOR),
        var usedTimes: TreeSet<UsedTime> = TreeSet(USED_TIME_COMPARATOR),
        var leftoverFreeTime: TimePart? = null
    ) : Cloneable {

    var planningHorizon = java.lang.Long.MAX_VALUE

    var freeLaborContent: Double = 0.0
        private set

    val hasFreeTime: Boolean
        get() = (leftoverFreeTime == null && freeLaborContent < 1)

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

    fun addResourceAvailability(availability: Availability) {
        resourceAvailabilityTable.addResourceAvailability(availability)
        when (availability) {
            is Availability.Periodic -> generateTimePartsByPeriodicAvailability(availability)
            is Availability.Occasional -> {
                val timePart = TimePart(availability.start, availability.duration, availability.capacity)
                freeTimes.add(timePart)
                freeLaborContent += timePart.laborContent
            }
            is Availability.Infinity -> {
                leftoverFreeTime = TimePart(availability.start, Long.MAX_VALUE, availability.capacity)
            }
        }
    }

    private fun generateTimePartsByPeriodicAvailability(availability: Availability.Periodic) {
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

    fun removeResourceAvailability(availability: Availability.Occasional) {
        resourceAvailabilityTable.removeResourceAvailability(availability)

        //todo remove on change
    }

    override fun clone(): Any {
        val clone = super.clone() as TimeSheet
        clone.resourceAvailabilityTable = resourceAvailabilityTable.getCopy()
        clone.freeTimes = freeTimes.map { it.getCopy() }.toCollection(TreeSet(TIME_PART_COMPARATOR))
        clone.usedTimes = usedTimes.map { it.getCopy() }.toCollection(TreeSet(USED_TIME_COMPARATOR))
        clone.leftoverFreeTime = leftoverFreeTime?.getCopy()
        return clone
    }

    fun getCopy(): TimeSheet {
        return clone() as TimeSheet
    }
}