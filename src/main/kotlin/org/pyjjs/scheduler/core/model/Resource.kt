package org.pyjjs.scheduler.core.model

import org.pyjjs.scheduler.core.placement.time.TimeSheet

class Resource(val name: String = "default",
               val type: String = "default",
               val timeSheet: TimeSheet = TimeSheet()) : IdentifiableObject()
