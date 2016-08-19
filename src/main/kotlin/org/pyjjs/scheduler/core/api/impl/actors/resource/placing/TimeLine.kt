package org.pyjjs.scheduler.core.api.impl.actors.resource.placing

import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.algorithms.PlacingAlgorithm
import java.util.*

class TimeLine {

    private val freeTimes = TreeSet<TimePart> { o1, o2 -> o1.start.compareTo(o2.start) }
    private val usedTimes = TreeSet<UsedTime> { o1, o2 -> o1.timePart.start.compareTo(o2.timePart.start) }

    private var placingAlgorithm: PlacingAlgorithm? = null
    private val planningHorizon = java.lang.Long.MAX_VALUE

    init {
        freeTimes.add(TimePart(0L))
    }

    fun setPlacingAlgorithm(placingAlgorithm: PlacingAlgorithm) {
        this.placingAlgorithm = placingAlgorithm
    }

    fun findNearestFreeTime(): TimePart? {
        val freeTimeIterator = freeTimes.iterator()
        if (freeTimeIterator.hasNext()) {
            return freeTimeIterator.next()
        }
        return null
    }

    fun findNearestUsedTime(): UsedTime? {
        val freeTimeIterator = usedTimes.iterator()
        if (freeTimeIterator.hasNext()) {
            return freeTimeIterator.next()
        }
        return null
    }

    inner class UsedTime(var tenant: Any) {
        var timePart: TimePart = TimePart()
    }

    inner class TimePart {
        var start: Long = 0L
        var duration: Long = 0L

        constructor()

        constructor(start: Long) {
            this.start = start
        }
    }
}
