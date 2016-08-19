package org.pyjjs.scheduler.core.api.impl.actors.resource.placing.algorithms

import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.TimeLine

interface PlacingAlgorithm {

    fun configurate(freeTimes: Set<TimeLine.TimePart>, usedTimes: Set<TimeLine.UsedTime>)

    fun findPlacement(duration: Long?, shared: Boolean)

}
