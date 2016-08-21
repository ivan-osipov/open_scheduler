package org.pyjjs.scheduler.core.placement.time

data class TimePart(var start: Long = 0L,
               var duration: Long = 0L,
               var capacity: Double = 0.toDouble()) {
    val laborContent: Double
        get() = duration*capacity
}