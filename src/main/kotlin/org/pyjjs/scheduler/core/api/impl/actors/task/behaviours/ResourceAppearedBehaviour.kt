package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.IFindAnyPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState

class ResourceAppearedBehaviour : TaskBehaviour<ResourceAppearedMessage>() {

    override fun perform(message: ResourceAppearedMessage) {
        if (hasNotPlacement()) {
            send(message.resourceRef, IFindAnyPlacementMessage(
                    actorRef,
                    actorState.source.descriptor,
                    actorState.source.resourceCriteria))
            actorState.status = TaskActorState.Status.WAIT_OFFER
        }
    }

    private fun hasNotPlacement(): Boolean {
        return TaskActorState.Status.UNPLACED == actorState.status
    }

    override fun processMessage(): Class<ResourceAppearedMessage> {
        return ResourceAppearedMessage::class.java
    }
}
