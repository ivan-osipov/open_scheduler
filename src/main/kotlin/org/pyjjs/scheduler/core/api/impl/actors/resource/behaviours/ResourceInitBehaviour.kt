package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceInitMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActorState
import kotlin.reflect.KClass

class ResourceInitBehaviour : Behaviour<ResourceActorState, ResourceInitMessage>() {

    override fun perform(message: ResourceInitMessage) {
        val resourceActorState = actorState
        resourceActorState.source = message.source
        resourceActorState.timeSheet = message.source.timeSheet
        resourceActorState.isInitialized = true
        saveActorState(resourceActorState)
    }

    override fun processMessage(): KClass<ResourceInitMessage> {
        return ResourceInitMessage::class
    }

}
