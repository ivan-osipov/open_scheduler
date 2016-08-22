package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.common.SystemConfigKeys
import org.pyjjs.scheduler.core.common.locale.LocaleMessageKeys
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState
import org.pyjjs.scheduler.core.api.impl.actors.task.messages.CheckOffersMessage

class OfferStoreBehaviour : TaskBehaviour<OfferMessage>() {

    override fun perform(message: OfferMessage) {
        logMessage(LocaleMessageKeys.TASK_RECEIVED_OFFER, message.sender!!.path().name(), actorRef.path().name(), message.placingPrice)
        val actorState = actorState
        actorState.addOffer(message)
        val checkOffersIsScheduled = actorState.checkOffersIsScheduled()

        if (!checkOffersIsScheduled) {
            scheduleOffersCheck(actorState)
        }
        saveActorState(actorState)
    }

    private fun scheduleOffersCheck(actorState: TaskActorState) {
        scheduleNotification(CheckOffersMessage(actorRef), SystemConfigKeys.DEFAULT_NOTIFICATION_DELAY_IN_MILLIS_KEY)
        actorState.setCheckOffersScheduled(true)
    }

    override fun processMessage(): Class<OfferMessage> {
        return OfferMessage::class.java
    }

}
