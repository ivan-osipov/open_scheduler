package org.pyjjs.scheduler.core.placement

import org.pyjjs.scheduler.core.placement.time.TimePart
import java.util.*

data class Offer(val id: UUID = UUID.randomUUID(), val offerParts: Set<TimePart> = HashSet(), val penalty: Double = 0.0)