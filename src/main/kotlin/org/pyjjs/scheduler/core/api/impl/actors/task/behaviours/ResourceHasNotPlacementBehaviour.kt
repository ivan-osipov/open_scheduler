package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasNotPlacementMessage

class ResourceHasNotPlacementBehaviour: TaskBehaviour<ResourceHasNotPlacementMessage>() {
    override fun perform(message: ResourceHasNotPlacementMessage) {
        //todo react
    }

    override fun processMessage(): Class<out ResourceHasNotPlacementMessage> {
        return ResourceHasNotPlacementMessage::class.java
    }

}