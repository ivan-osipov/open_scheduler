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
        //TODO: check task restrictions
        val affectedFreeTimes = HashSet<TimePart>()
        val offeredFreeTimes = HashSet<TimePart>()

        var remainedLaborContent = taskDescriptor.laborContent
        for (freeTime in timeSheet.freeTimes) {
            affectedFreeTimes.add(freeTime)
            if(remainedLaborContent < freeTime.laborContent) {
                val usedDuration = Math.round(remainedLaborContent / freeTime.capacity).toLong()
                val usedCapacity = remainedLaborContent / usedDuration
                val usedFreeTime = TimePart(freeTime.start, usedDuration, usedCapacity)
                offeredFreeTimes.add(usedFreeTime)

                val remainedCapacity = freeTime.capacity - usedCapacity
                if(remainedCapacity > 0) {
                    val remainedFreeTimeByCapacity = TimePart(
                            freeTime.start,
                            usedDuration,
                            remainedCapacity)
                    timeSheet.freeTimes.add(remainedFreeTimeByCapacity)
                }

                val unusedDuration = freeTime.duration - usedDuration
                val remainedFreeTimeByDuration = TimePart(
                        freeTime.start + usedDuration,
                        unusedDuration,
                        freeTime.capacity)
                timeSheet.freeTimes.add(remainedFreeTimeByDuration)
                remainedLaborContent = 0.0
                break
            } else {
                remainedLaborContent -= freeTime.laborContent
                offeredFreeTimes.add(freeTime)
                if(remainedLaborContent == 0.0) break
            }
        }
        timeSheet.freeTimes.removeAll(affectedFreeTimes)

        val penalty: Double
        val placementType: Placement.Type = if(remainedLaborContent == 0.0) {
            penalty = 0.0
            Placement.Type.FULL
        } else {
            penalty = 1.0
            Placement.Type.PARTIAL
        }
        val offer = Offer(Collections.unmodifiableSet(offeredFreeTimes), penalty)
        return Placement(placementType, offer)
    }
}
