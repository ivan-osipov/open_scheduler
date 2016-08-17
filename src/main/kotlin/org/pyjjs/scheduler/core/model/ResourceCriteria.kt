package org.pyjjs.scheduler.core.model

import org.pyjjs.scheduler.core.model.criteria.SoftCriterion
import org.pyjjs.scheduler.core.model.criteria.StrictCriterion
import java.util.*

class ResourceCriteria {
//        var maxCapacity: Double?,
//        var minCapacity: Double?,
//        var resourceTypeAlternatives: Set<ResourceType>,
//        var availableLaborContent: AvailableLaborContent

    var softCriteria: MutableSet<SoftCriterion<*>> = HashSet()
    var strictCriterion: MutableSet<StrictCriterion<*>> = HashSet()
}

//class AvailableLaborContent(
//        var laborContent: Double,
//        var interval: DateRange
//)