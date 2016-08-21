package org.pyjjs.scheduler.core.placement

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import org.pyjjs.scheduler.core.placement.time.TimeSheet

interface PlacementFinder {
    fun findAnyPlacement(taskDescriptor: TaskDescriptor, timeSheet: TimeSheet): Placement
}