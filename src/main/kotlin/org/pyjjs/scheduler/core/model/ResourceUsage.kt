package org.pyjjs.scheduler.core.model

class ResourceUsage : IdentifiableObject() {

    var resource: Resource? = null
    var dateRange: DateRange = DateRange()
    var resourceUser: Task? = null
}
