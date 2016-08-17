package org.pyjjs.scheduler.core.model

class ResourceUsage(var dateRange: DateRange,
                    var resource: Resource? = null,
                    var resourceUser: Task? = null) : IdentifiableObject()
