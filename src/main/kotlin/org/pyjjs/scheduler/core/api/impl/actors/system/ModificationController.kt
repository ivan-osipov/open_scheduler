package org.pyjjs.scheduler.core.api.impl.actors.system

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.DataSourceChangeBehaviour

class ModificationController(taskSupervisor: ActorRef, resourceSupervisor: ActorRef) : BehaviourBasedActor<ModificationControllerState>() {

    init {
        val modificationControllerState = ModificationControllerState(context, taskSupervisor, resourceSupervisor)
        updateActorState(modificationControllerState)
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Modification controller is initialized")
    }

    private fun fillBehaviours() {
        addBehaviour(DataSourceChangeBehaviour::class)
    }
}
