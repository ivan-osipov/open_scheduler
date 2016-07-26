package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.model.ResourceUsage
import java.util.*

interface Plan {
    var lastUpdate: Long;
    val resourceUsages: SortedSet<ResourceUsage>
}
