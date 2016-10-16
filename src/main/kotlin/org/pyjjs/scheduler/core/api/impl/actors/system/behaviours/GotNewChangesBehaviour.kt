package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.PROPOSED_LEAD_TIME
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.CheckNewChanges
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.api.impl.strategies.ContextImpl
import org.pyjjs.scheduler.core.common.SystemConfigKeys
import org.pyjjs.scheduler.core.model.Task
import kotlin.reflect.KClass

class GotNewChangesBehaviour : Behaviour<SchedulingControllerState, PlanUpdatedMessage>() {
    override fun perform(message: PlanUpdatedMessage) {
        actorState.planChanges.addAll(message.planChanges)
        BEHAVIOUR_LOG.info("Actor ${getActorLocalName(message.sender!!)} notify about changes: ${message.planChanges}")
        actorState.unplacedTasks.remove(message.sender)
        BEHAVIOUR_LOG.info("Remained tasks amount: ${actorState.unplacedTasks.size}")
        val tasksByActor = actorState.tasks.inverse()
        val task = checkNotNull(tasksByActor[message.sender], {"Unknown task"})

        updateTaskWithChanges(message, task)

        if(actorState.unplacedTasks.isEmpty()) {
            schedulePlanSave()
        }

        updateTaskDiscontent(message.sender, task, actorState)
        saveActorState(actorState)
    }

    private fun updateTaskWithChanges(message: PlanUpdatedMessage, task: Task) {
        message.planChanges.forEach {
            when (it.type) {
                PlanChange.Type.INSERT, PlanChange.Type.UPDATE -> task.result.resourceUsages.add(it.resourceUsage)
                PlanChange.Type.REMOVE -> task.result.resourceUsages.remove(it.resourceUsage)
            }
        }
    }

    private fun schedulePlanSave() {
        val checkChangesAreScheduled = actorState.checkOffersAreScheduled()

        if (!checkChangesAreScheduled) {
            scheduleNotification(CheckNewChanges(), SystemConfigKeys.SCHEDULE_CONTROLLER_WAITING_IN_MILLIS_KEY)
            actorState.setCheckOffersAreScheduled(true)
        }
    }

    private fun updateTaskDiscontent(sender: ActorRef, task: Task, actorState: SchedulingControllerState) {
        val resourceUsage = task.result.resourceUsages.maxBy { it.timePart.end }
        val taskObjectiveFunction = actorState.registryOfStrategies.fetch(task.descriptor.strategy)
        val context = ContextImpl()
        if (resourceUsage != null) {
            context.put(PROPOSED_LEAD_TIME, resourceUsage.timePart.end)
        }
        actorState.discontentsByTaskActors[sender] = Math.abs(taskObjectiveFunction.calculate(task, context))
    }

    override fun processMessage(): KClass<PlanUpdatedMessage> {
        return PlanUpdatedMessage::class
    }
}
