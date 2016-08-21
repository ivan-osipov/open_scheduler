package org.pyjjs.scheduler.core.placement.time

import org.pyjjs.scheduler.core.model.IdentifiableObject

class ResourceAvailability(var start: Long, var duration: Long, var capacity: Double) : IdentifiableObject()