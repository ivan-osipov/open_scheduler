package org.pyjjs.scheduler.core.model

import java.util.Date

import com.google.common.base.Preconditions.checkNotNull

class DateRange(start: Date?, end: Date?) {

    var start: Date
    var end: Date

    init {
        checkNotNull(start)
        checkNotNull(end)
        this.start = Date(start?.time ?: 0)
        this.end = Date(end?.time ?: 0)
    }

    constructor() : this(null, null)

    val duration: Long
        get() = end.time - start.time

}
