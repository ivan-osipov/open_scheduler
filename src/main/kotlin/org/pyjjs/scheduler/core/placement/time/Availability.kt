package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.model.IdentifiableObject

sealed class Availability(var start: Long, var capacity: Double) : IdentifiableObject(){

    init {
        check(start >= 0, { "Start time is incorrect" })
        check(capacity > 0, { "Capacity is incorrect" })
    }

    open class Occasional(start: Long, var duration: Long, capacity: Double) : Availability(start, capacity) {
        init {
            check(duration > 0, { "Duration is incorrect" })
        }
    }

    class Periodic(start: Long, duration: Long, capacity: Double,
                   val interval: Long, val end: Long): Availability.Occasional(start, duration, capacity) {
        init {
            check(interval > duration, { "Disturbed constrain: interval > duration" })
            check(end - start > interval, { "Disturbed constrain: end - start > interval" })
        }
    }

    class Infinity(start: Long, capacity: Double): Availability(start, capacity)

}
