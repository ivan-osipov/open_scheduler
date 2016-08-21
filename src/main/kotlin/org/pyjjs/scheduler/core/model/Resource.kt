package org.pyjjs.scheduler.core.model

import org.pyjjs.scheduler.core.placement.time.TimeSheet

class Resource(val timeSheet: TimeSheet = TimeSheet()) : IdentifiableObject()
