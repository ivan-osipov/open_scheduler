package org.pyjjs.scheduler.core.api.impl.actors.resource

import org.pyjjs.scheduler.core.api.impl.actors.common.*
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours.FindPlacementBehaviour
import org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours.ResourceInitBehaviour
import org.pyjjs.scheduler.core.model.Resource

class ResourceActor : BehaviourBasedActor<ResourceActorState>(), HasObjectiveFunction {

    override fun createInitialState(): ResourceActorState = ResourceActorState(context)

    override fun calculateObjectiveFunction(): Double {
        return 0.0
    }

    override fun init() {
        fillBehaviours()
    }

    private fun fillBehaviours() {
        addBehaviour(ResourceInitBehaviour::class.java)
        addBehaviour(FindPlacementBehaviour::class.java)
    }
}
