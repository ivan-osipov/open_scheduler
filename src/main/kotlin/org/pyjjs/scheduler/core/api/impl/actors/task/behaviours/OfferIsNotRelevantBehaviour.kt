package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.IFindAnyPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.OfferAcceptedMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.OfferIsNotRelevantMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState

class OfferIsNotRelevantBehaviour: TaskBehaviour<OfferIsNotRelevantMessage>() {
    override fun perform(message: OfferIsNotRelevantMessage) {
        val placementMessageQueue = actorState.placementMessageQueue

        //todo fetch the best
        val placementMessage = placementMessageQueue.poll()
        placementMessage ?: apply {
            sendRequestAgain(message.sender!!)
            return
        }
        val offer = placementMessage.offer;
        actorState.offersById.remove(message.offerId)
        answer(placementMessage, OfferAcceptedMessage(actorRef, offer.id))
        actorState.status = TaskActorState.Status.WAIT_RESERVING
        saveActorState(actorState)
    }

    private fun sendRequestAgain(sender: ActorRef) {
        send(sender, IFindAnyPlacementMessage(
                actorRef,
                actorState.source.descriptor,
                actorState.source.resourceCriteria))
        actorState.status = TaskActorState.Status.WAIT_OFFER
        saveActorState(actorState)
    }

    override fun processMessage(): Class<out OfferIsNotRelevantMessage> {
        return OfferIsNotRelevantMessage::class.java
    }

}
