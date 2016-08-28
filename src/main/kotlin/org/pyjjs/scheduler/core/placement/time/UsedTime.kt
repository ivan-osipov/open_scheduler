package org.pyjjs.scheduler.core.placement.time

class UsedTime(var tenant: Any, var timePart:TimePart = TimePart()) : Cloneable {

    override fun clone(): Any {
        val clone = super.clone() as UsedTime
        clone.timePart = timePart.getCopy()
        return clone
    }

    fun getCopy(): UsedTime {
        return clone() as UsedTime
    }
}