package org.pyjjs.scheduler.core.placement

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import org.pyjjs.scheduler.core.model.schedule_specific.Offer
import org.pyjjs.scheduler.core.placement.time.TimePart
import org.pyjjs.scheduler.core.placement.time.TimeSheet
import java.util.*

class JitPlacementFinder : PlacementFinder {
    override fun findAnyPlacement(taskDescriptor: TaskDescriptor, timeSheet: TimeSheet): Placement {
        if(timeSheet.freeLaborContent < 1) {
            return Placement(Placement.Type.IMPOSSIBLY)
        }

        val usedFreeTimes = HashSet<TimePart>()

        var remainedLaborContent = taskDescriptor.laborContent
        for (freeTime in timeSheet.freeTimes) {
            usedFreeTimes.add(freeTime)
            if(remainedLaborContent < freeTime.laborContent) {
                val minDuration = Math.round(remainedLaborContent / freeTime.capacity).toLong()
                freeTime.start = freeTime.start + minDuration
                freeTime.duration = freeTime.duration - minDuration
                freeTime.capacity = remainedLaborContent / minDuration
                remainedLaborContent = 0.0
                break
            } else {
                remainedLaborContent -= freeTime.laborContent
                usedFreeTimes.add(freeTime)
            }
        }
        timeSheet.freeTimes.removeAll(usedFreeTimes)

        var penalty: Double
        val placementType: Placement.Type = if(remainedLaborContent == 0.0) {
            penalty = 0.0
            Placement.Type.FULL
        } else {
            penalty = 1.0
            Placement.Type.PARTIAL
        }
        val offer = Offer(Collections.unmodifiableSet(usedFreeTimes), penalty)
        return Placement(placementType, offer)
    }
}
