package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.system.messages.PlanUpdatedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.messages.CheckOffersMessage
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.common.locale.LocaleMessageKeys

class OffersCheckBehaviour : TaskBehaviour<CheckOffersMessage>() {

    override fun perform(message: CheckOffersMessage) {
        logMessage(LocaleMessageKeys.TASK_PROCESS_OFFERS, actorLocalName)
        val resourcePrices = actorState.getOffers()
        val offer = resourcePrices.minBy { it.placingPrice }
        logMessage(LocaleMessageKeys.TASK_FOUND_BEST_OFFER, actorLocalName, offer!!.placingPrice, getActorLocalName(offer.sender!!))

        //DEMO REACT
        val planUpdatedMessage = PlanUpdatedMessage()
        planUpdatedMessage.planChanges.add(PlanChange.insert()
                .task(actorState.source)
                .resource(offer.resource!!))
        actorState.actorSystem.eventStream().publish(planUpdatedMessage)
        //TODO react
    }

    override fun processMessage(): Class<CheckOffersMessage> {
        return CheckOffersMessage::class.java
    }
}
