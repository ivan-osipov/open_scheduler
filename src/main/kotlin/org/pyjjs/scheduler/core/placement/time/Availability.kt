package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.model.IdentifiableObject

sealed class Availability(var start: Long, var capacity: Double) : IdentifiableObject(), Cloneable {

    init {
        check(start >= 0, { "Start time is incorrect" })
        check(capacity > 0, { "Capacity is incorrect" })
    }

    abstract fun getCopy(): Availability

    open class Occasional(start: Long, var duration: Long, capacity: Double) : Availability(start, capacity), Cloneable {
        init {
            check(duration > 0, { "Duration is incorrect" })
        }

        override fun getCopy(): Occasional {
            return clone() as Occasional
        }
    }

    class Periodic(start: Long, duration: Long, capacity: Double,
                   val interval: Long, val end: Long): Availability.Occasional(start, duration, capacity), Cloneable {
        init {
            check(interval > duration, { "Disturbed constrain: interval > duration" })
            check(end - start > interval, { "Disturbed constrain: end - start > interval" })
        }

        override fun getCopy(): Periodic {
            return clone() as Periodic
        }
    }

    class Infinity(start: Long, capacity: Double): Availability(start, capacity), Cloneable {
        override fun getCopy(): Infinity {
            return clone() as Infinity
        }
    }

}
