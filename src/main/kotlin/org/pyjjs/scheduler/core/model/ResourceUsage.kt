package org.pyjjs.scheduler.core.model

import org.pyjjs.scheduler.core.placement.time.TimePart

class ResourceUsage(var timePart: TimePart,
                    var resource: Resource,
                    var task: Task) : IdentifiableObject() {
    override fun toString(): String{
        return "ResourceUsage(timePart=$timePart, resource=$resource, task=$task)"
    }
}
