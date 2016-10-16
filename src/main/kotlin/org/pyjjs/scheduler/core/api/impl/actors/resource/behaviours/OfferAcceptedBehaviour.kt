package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.OfferAcceptedMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.OfferIsNotRelevantMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.PlacementReservedMessage
import kotlin.reflect.KClass

class OfferAcceptedBehaviour : ResourceBehaviour<OfferAcceptedMessage>() {
    override fun perform(message: OfferAcceptedMessage) {
        val timeSheet = actorState.timeSheetsByOfferId[message.offerId]
        if(timeSheet == null) {
            BEHAVIOUR_LOG.info("Resource $actorLocalName rejects accept from task: ${getActorLocalName(message.sender!!)}")
            answer(message, OfferIsNotRelevantMessage(actorRef, message.offerId))
        } else {
            BEHAVIOUR_LOG.info("Resource $actorLocalName confirm offer for task: ${getActorLocalName(message.sender!!)}")
            actorState.timeSheet = timeSheet
            actorState.timeSheetsByOfferId.clear()
            saveActorState(actorState)

            answer(message, PlacementReservedMessage(actorRef, message.offerId))
        }
    }

    override fun processMessage(): KClass<out OfferAcceptedMessage> {
        return OfferAcceptedMessage::class
    }

}
