package org.pyjjs.scheduler.core.placement.time

class PeriodicResourceAvailability(start: Long,
                                   duration: Long,
                                   capacity: Double,
                                   val interval: Long,
                                   val end: Long): ResourceAvailability(start, duration, capacity) {
    init {
        check(interval > duration, { "Disturbed constrain: interval > duration" })
        check(end - start > interval, { "Disturbed constrain: end - start > interval" })
    }
}