package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.model.IdentifiableObject

open class ResourceAvailability(var start: Long, var duration: Long, var capacity: Double) : IdentifiableObject()