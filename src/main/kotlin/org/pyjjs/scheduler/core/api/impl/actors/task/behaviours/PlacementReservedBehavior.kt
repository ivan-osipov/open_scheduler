package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.PlacementReservedMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.model.ResourceUsage
import kotlin.reflect.KClass

class PlacementReservedBehavior: TaskBehaviour<PlacementReservedMessage>() {
    override fun perform(message: PlacementReservedMessage) {
        actorState.status = TaskActorState.Status.PLACED
        val offerMessage = getOfferById(message.offerId)
        offerMessage ?: throw IllegalStateException("Offer not found")
        BEHAVIOUR_LOG.info("Task $actorLocalName found placement on resource ${getActorLocalName(offerMessage.sender!!)} " +
                "${offerMessage.offer}")

        val planChanges = offerMessage.offer.offerParts
                .map {
                    PlanChange(PlanChange.Type.INSERT,
                            ResourceUsage(it,
                                    offerMessage.resource,
                                    this.actorState.source))
                }
        val planUpdatedMessage = PlanUpdatedMessage(actorRef)
        planUpdatedMessage.planChanges.addAll(planChanges)
        BEHAVIOUR_LOG.info("Task $actorLocalName broadcast notification about placement")
        actorState.actorSystem.eventStream().publish(planUpdatedMessage)

        saveActorState(actorState)
    }

    override fun processMessage(): KClass<out PlacementReservedMessage> {
        return PlacementReservedMessage::class
    }

}
