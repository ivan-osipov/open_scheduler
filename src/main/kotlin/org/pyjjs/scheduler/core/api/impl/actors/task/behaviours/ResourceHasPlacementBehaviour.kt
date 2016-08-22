package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasPlacementMessage

class ResourceHasPlacementBehaviour: TaskBehaviour<ResourceHasPlacementMessage>() {

    override fun perform(message: ResourceHasPlacementMessage) {
        //todo react
    }

    override fun processMessage(): Class<out ResourceHasPlacementMessage> {
        return ResourceHasPlacementMessage::class.java
    }

}