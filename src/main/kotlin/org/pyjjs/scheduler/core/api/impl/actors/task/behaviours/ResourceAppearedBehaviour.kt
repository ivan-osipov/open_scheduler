package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.IFindAnyPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.messages.ResourceAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState

class ResourceAppearedBehaviour : TaskBehaviour<ResourceAppearedMessage>() {

    override fun perform(message: ResourceAppearedMessage) {
        val state = actorState
        if (hasNotPlacement(state)) {
            send(message.resourceRef, IFindAnyPlacementMessage(
                    actorRef,
                    state.source.descriptor,
                    state.source.resourceCriteria))
        }
    }

    private fun hasNotPlacement(taskActorState: TaskActorState): Boolean {
        return taskActorState.discontent == null
    }

    override fun processMessage(): Class<ResourceAppearedMessage> {
        return ResourceAppearedMessage::class.java
    }
}
