package org.pyjjs.scheduler.core.placement

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import org.pyjjs.scheduler.core.api.impl.utils.*
import org.pyjjs.scheduler.core.placement.time.TimePart
import org.pyjjs.scheduler.core.placement.time.TimeSheet
import org.pyjjs.scheduler.core.placement.time.UsedTime
import java.util.*

class PlacementFinderImpl : PlacementFinder {
    override fun findAnyPlacement(taskDescriptor: TaskDescriptor, timeSheet: TimeSheet): Placement {
        if(timeSheet.hasFreeTime) {
            return Placement(Placement.Type.IMPOSSIBLY)
        }

        val freeTimes = timeSheet.freeTimes.filter {
            timePartSuitsByTaskDescriptor(it, taskDescriptor)
        }.toCollection(TreeSet(TIME_PART_COMPARATOR))

        if(timeSheet.freeTimes.isEmpty()) {
            return Placement(Placement.Type.IMPOSSIBLY)
        }

        val affectedFreeTimes = HashSet<TimePart>()
        val offeredFreeTimes = HashSet<TimePart>()

        var remainedLaborContent = taskDescriptor.laborContent
        for (freeTime in freeTimes) {
            affectedFreeTimes.add(freeTime)
            if(remainedLaborContent < freeTime.laborContent) {
                //todo need balance between capacity and duration
                val usedDuration = Math.round(remainedLaborContent / (Math.min(taskDescriptor.maxCapacity, freeTime.capacity))).toLong()
                val usedCapacity = remainedLaborContent / usedDuration
                val usedFreeTime = TimePart(freeTime.start, usedDuration, usedCapacity)
                offeredFreeTimes.add(usedFreeTime)

                val remainedCapacity = freeTime.capacity - usedCapacity
                if(remainedCapacity > 0) {
                    val remainedFreeTimeByCapacity = TimePart(
                            freeTime.start,
                            usedDuration,
                            remainedCapacity)
                    freeTimes.add(remainedFreeTimeByCapacity)
                }

                val unusedDuration = freeTime.duration - usedDuration
                val remainedFreeTimeByDuration = TimePart(
                        freeTime.start + usedDuration,
                        unusedDuration,
                        freeTime.capacity)
                freeTimes.add(remainedFreeTimeByDuration)
                remainedLaborContent = 0.0
                break
            } else {
                remainedLaborContent -= freeTime.laborContent
                offeredFreeTimes.add(freeTime)
                if(remainedLaborContent == 0.0) break
            }
        }
        freeTimes.removeAll(affectedFreeTimes)

        val penalty: Double
        val placementType: Placement.Type = if(remainedLaborContent == 0.0) {
            penalty = 0.0
            Placement.Type.FULL
        } else {
            penalty = 1.0
            Placement.Type.PARTIAL
        }
        timeSheet.freeTimes = freeTimes
        timeSheet.usedTimes.addAll(offeredFreeTimes.map { UsedTime(taskDescriptor, it) })
        val offer = Offer(offerParts = Collections.unmodifiableSet(offeredFreeTimes), penalty = penalty)
        return Placement(placementType, offer)
    }

    private fun timePartSuitsByTaskDescriptor(timePart: TimePart, taskDescriptor: TaskDescriptor): Boolean {
        return timePart.capacity >= taskDescriptor.minCapacity && timePart.capacity <= taskDescriptor.maxCapacity &&
                timePart.duration >= taskDescriptor.minDuration
    }
}
