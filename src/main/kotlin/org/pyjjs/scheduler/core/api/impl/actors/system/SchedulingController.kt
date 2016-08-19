package org.pyjjs.scheduler.core.api.impl.actors.system

import com.google.common.collect.Sets
import org.pyjjs.scheduler.core.api.SchedulingListener
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.GotNewChangesBehaviour
import org.pyjjs.scheduler.core.api.impl.actors.system.behaviours.NotifyAboutChangesBehaviour
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SchedulingController(val listeners: SchedulingListener) : BehaviourBasedActor<SchedulingControllerState>() {

    override fun createInitialState(): SchedulingControllerState {
        val schedulingControllerState = SchedulingControllerState(context)
        schedulingControllerState.schedulingListeners = Sets.newHashSet(listeners)
        schedulingControllerState.isInitialized = true
        return schedulingControllerState
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Scheduling controller is initialized")
    }

    private fun fillBehaviours() {
        addBehaviour(GotNewChangesBehaviour::class.java)
        addBehaviour(NotifyAboutChangesBehaviour::class.java)
    }
}
