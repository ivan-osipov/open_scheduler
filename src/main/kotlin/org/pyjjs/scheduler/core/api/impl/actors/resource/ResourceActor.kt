package org.pyjjs.scheduler.core.api.impl.actors.resource

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours.FindPlacementBehaviour
import org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours.OfferAcceptedBehaviour
import org.pyjjs.scheduler.core.model.Resource

class ResourceActor(source: Resource) : BehaviourBasedActor<ResourceActorState>() {

    init {
        val actorState = ResourceActorState(context)
        actorState.source = source
        actorState.timeSheet = source.timeSheet
        updateActorState(actorState)
    }

    override fun init() {
        fillBehaviours()
    }

    private fun fillBehaviours() {
        addBehaviour(FindPlacementBehaviour::class.java)
        addBehaviour(OfferAcceptedBehaviour::class.java)
    }
}
