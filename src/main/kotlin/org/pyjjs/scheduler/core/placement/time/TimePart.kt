package org.pyjjs.scheduler.core.placement.time

data class TimePart(var start: Long = 0L,
               var duration: Long = 0L,
               var capacity: Double = 0.toDouble()) : Cloneable {

    val laborContent: Double
        get() = duration*capacity

    fun getCopy(): TimePart {
        return clone() as TimePart
    }
}