package org.pyjjs.scheduler.core

import org.pyjjs.scheduler.core.model.IdentifiableObject

class ResourceAvailability(var start: Long, var end: Long, var capacity: Double) : IdentifiableObject()