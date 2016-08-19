package org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.behaviours.CreateResourceBehaviour

class ResourceSupervisor : BehaviourBasedActor<ResourceSupervisorState>() {

    override fun createInitialState(): ResourceSupervisorState {
        val resourceSupervisorState = ResourceSupervisorState(context)
        resourceSupervisorState.isInitialized = true
        return resourceSupervisorState
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Resource Supervisor initialized")
    }

    private fun fillBehaviours() {
        addBehaviour(CreateResourceBehaviour::class.java)
    }
}
