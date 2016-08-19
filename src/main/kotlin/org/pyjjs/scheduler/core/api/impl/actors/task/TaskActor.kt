package org.pyjjs.scheduler.core.api.impl.actors.task

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.task.behaviours.*

class TaskActor : BehaviourBasedActor<TaskActorState>() {

    override fun createInitialState(): TaskActorState = TaskActorState(context)

    override fun init() {
        fillBehaviours()
        LOG.info("Task Actor started: " + this.self.path().toString())
    }

    private fun fillBehaviours() {
        addBehaviour(TaskInitBehaviour::class.java)
        addBehaviour(ResourceAppearedBehaviour::class.java)
        addBehaviour(OfferStoreBehaviour::class.java)
        addBehaviour(OffersCheckBehaviour::class.java)
    }
}
