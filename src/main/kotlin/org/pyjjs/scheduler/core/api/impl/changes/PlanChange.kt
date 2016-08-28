package org.pyjjs.scheduler.core.api.impl.changes

import org.pyjjs.scheduler.core.api.impl.interfaces.HasTimestamp
import org.pyjjs.scheduler.core.model.DateRange
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.ResourceUsage
import org.pyjjs.scheduler.core.model.Task

import java.util.Date
import java.util.UUID

data class PlanChange(val type: Type, val resourceUsage: ResourceUsage) : HasTimestamp {

    val id: UUID = UUID.randomUUID()
    override var timestamp: Long = Date().time

    enum class Type {
        INSERT, UPDATE, REMOVE
    }
}
