package org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.behaviours.CreateResourceBehaviour

class ResourceSupervisor : BehaviourBasedActor<ResourceSupervisorState>() {

    init {
        val resourceSupervisorState = ResourceSupervisorState(context)
        updateActorState(resourceSupervisorState)
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Resource Supervisor initialized")
    }

    private fun fillBehaviours() {
        addBehaviour(CreateResourceBehaviour::class)
    }
}
