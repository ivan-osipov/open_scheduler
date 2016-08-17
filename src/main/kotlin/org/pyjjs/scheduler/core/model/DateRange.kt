package org.pyjjs.scheduler.core.model

class DateRange(var start: Long, var end: Long) {

    val duration: Long
        get() = end - start

}
