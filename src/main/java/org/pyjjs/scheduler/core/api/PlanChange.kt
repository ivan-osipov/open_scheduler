package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.ResourceUsage
import org.pyjjs.scheduler.core.model.Task

import java.util.Date
import java.util.UUID

class PlanChange private constructor(val type: PlanChange.ChangeType) {

    enum class ChangeType {
        INSERT, UPDATE, REMOVE
    }

    val id = UUID.randomUUID()

    val timestamp: Long = Date().time

    val resourceUsage: ResourceUsage

    init {
        resourceUsage = ResourceUsage()
    }

    fun task(task: Task): PlanChange {
        resourceUsage.resourceUser = task
        return this
    }

    fun resource(resource: Resource): PlanChange {
        resourceUsage.resource = resource
        return this
    }

    fun start(start: Date): PlanChange{
        resourceUsage.dateRange.start = start;
        return this
    }

    fun end(end: Date): PlanChange{
        resourceUsage.dateRange.end = end;
        return this
    }

    companion object {

        fun insert(): PlanChange {
            return PlanChange(ChangeType.INSERT)
        }

        fun update(): PlanChange {
            return PlanChange(ChangeType.UPDATE)
        }

        fun remove(): PlanChange {
            return PlanChange(ChangeType.REMOVE)
        }
    }
}
