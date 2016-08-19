package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.IFindAnyPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceNotMatchMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActorState
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage
import org.pyjjs.scheduler.core.common.locale.LocaleMessageKeys
import org.pyjjs.scheduler.core.model.criteria.match

class FindPlacementBehaviour : Behaviour<ResourceActorState, IFindAnyPlacementMessage>() {

    override fun perform(message: IFindAnyPlacementMessage) {
        val resourceActorState = actorState
        val resource = resourceActorState.source
        val resourceCriteria = message.resourceCriteria
        if(resource match resourceCriteria.strictCriterion) {

            val answer = OfferMessage(actorRef)
            answer.placingPrice = resourceActorState.placingPrice
            answer.resource = actorState.source
            answer(message, answer)
        } else {
            logMessage(LocaleMessageKeys.RESOURCE_NOT_MATCH_FOR_CRITERIA, actorLocalName, message.sender?.path()?.name() ?: "UNNAMED")
            answer(message, ResourceNotMatchMessage(actorRef))
        }
    }

    override fun processMessage(): Class<IFindAnyPlacementMessage> {
        return IFindAnyPlacementMessage::class.java
    }
}
