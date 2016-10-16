package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.RejectionReason
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasNotPlacementMessage
import kotlin.reflect.KClass

class ResourceHasNotPlacementBehaviour: TaskBehaviour<ResourceHasNotPlacementMessage>() {
    override fun perform(message: ResourceHasNotPlacementMessage) {
        if(message.sender == null) return

        when(message.rejectionReason) {
            RejectionReason.NOT_MATCH -> actorState.addNotMatchedResource(message.sender)
            RejectionReason.HAS_NOT_FREE_TIME -> actorState.addResourceWithoutFreeTimes(message.sender)
        }

        saveActorState(actorState)
    }

    override fun processMessage(): KClass<out ResourceHasNotPlacementMessage> {
        return ResourceHasNotPlacementMessage::class
    }

}