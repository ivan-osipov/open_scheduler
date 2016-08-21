package org.pyjjs.scheduler.core.api.impl.utils

import org.pyjjs.scheduler.core.placement.time.ResourceAvailability
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.model.ResourceUsage
import org.pyjjs.scheduler.core.placement.time.TimePart
import org.pyjjs.scheduler.core.placement.time.UsedTime

object Comparators {

    val TIMESTAMP_COMPARATOR = { o1: PlanChange, o2: PlanChange ->
        val compareResult = java.lang.Long.compare(o1.timestamp, o2.timestamp)
        if (compareResult == 0) 1 else compareResult
    }

    val RESOURCE_USAGE_COMPARATOR = { ru1: ResourceUsage, ru2: ResourceUsage ->
        if (ru1.id == ru2.id) 0
        else {
            val compareResult = ru1.dateRange.start.compareTo(ru2.dateRange.start)
            if (compareResult == 0) 1 else compareResult
        }
    }

    val RESOURCE_AVAILABILITY_COMPARATOR = { ru1: ResourceAvailability, ru2: ResourceAvailability ->
        if (ru1.id == ru2.id) 0
        else {
            val compareResult = ru1.start.compareTo(ru2.start)
            if (compareResult == 0) 1 else compareResult
        }
    }

    val TIME_PART_COMPARATOR = { o1: TimePart, o2: TimePart -> o1.start.compareTo(o2.start) }

    val USED_TIME_COMPARATOR = { o1: UsedTime, o2: UsedTime -> o1.timePart.start.compareTo(o2.timePart.start) }


}
