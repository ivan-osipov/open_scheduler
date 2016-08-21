package org.pyjjs.scheduler.core.model.schedule_specific

import org.pyjjs.scheduler.core.placement.time.TimePart
import java.util.*

data class Offer(val offerParts: Set<TimePart> = HashSet(), val penalty: Double = 0.0)