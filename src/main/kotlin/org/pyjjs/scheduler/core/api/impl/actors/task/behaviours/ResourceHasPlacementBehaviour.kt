package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.OfferAcceptedMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasFullPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState
import kotlin.reflect.KClass

class ResourceHasPlacementBehaviour: TaskBehaviour<ResourceHasPlacementMessage>() {

    override fun perform(message: ResourceHasPlacementMessage) {
        when(actorState.status) {
            TaskActorState.Status.UNPLACED, TaskActorState.Status.WAIT_OFFER -> {
                val offer = message.offer
                if (message is ResourceHasFullPlacementMessage) {
                    actorState.offersById[offer.id] = message
                    answer(message, OfferAcceptedMessage(actorRef, offer.id))
                    actorState.status = TaskActorState.Status.WAIT_RESERVING
                } else {
                    BEHAVIOUR_LOG.info("Partial offer skip")
                }
            }
            TaskActorState.Status.WAIT_RESERVING -> {
                if (message is ResourceHasFullPlacementMessage) {
                    actorState.placementMessageQueue.add(message)
                }
            }
            TaskActorState.Status.PLACED -> {
                //todo calc obj func
            }
        }
    }

    override fun processMessage(): KClass<out ResourceHasPlacementMessage> {
        return ResourceHasPlacementMessage::class
    }

}