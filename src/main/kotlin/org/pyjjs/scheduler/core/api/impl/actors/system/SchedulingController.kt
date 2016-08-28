package org.pyjjs.scheduler.core.api.impl.actors.system

import org.pyjjs.scheduler.core.api.SchedulingListener
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.GotNewChangesBehaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.NotifyAboutChangesBehaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.TaskAppearedBehaviour

class SchedulingController(listener: SchedulingListener) : BehaviourBasedActor<SchedulingControllerState>() {

    init {
        val schedulingControllerState = SchedulingControllerState(context)
        schedulingControllerState.schedulingListeners.add(listener)
        updateActorState(schedulingControllerState)
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Scheduling controller is initialized")
    }

    private fun fillBehaviours() {
        addBehaviour(GotNewChangesBehaviour::class.java)
        addBehaviour(NotifyAboutChangesBehaviour::class.java)
        addBehaviour(TaskAppearedBehaviour::class.java)
    }
}
