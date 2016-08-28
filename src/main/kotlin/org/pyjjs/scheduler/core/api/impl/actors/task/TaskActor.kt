package org.pyjjs.scheduler.core.api.impl.actors.task

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.task.behaviours.*
import org.pyjjs.scheduler.core.model.Task

class TaskActor(task: Task) : BehaviourBasedActor<TaskActorState>() {

    init {
        val taskActorState = TaskActorState(context)
        taskActorState.source = task
        updateActorState(taskActorState)
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Task Actor started: " + this.self.path().toString())
    }

    private fun fillBehaviours() {
        addBehaviour(ResourceAppearedBehaviour::class.java)
        addBehaviour(OfferStoreBehaviour::class.java)
        addBehaviour(OffersCheckBehaviour::class.java)
        addBehaviour(ResourceHasPlacementBehaviour::class.java)
        addBehaviour(ResourceHasNotPlacementBehaviour::class.java)
        addBehaviour(PlacementReservedBehavior::class.java)
        addBehaviour(OfferIsNotRelevantBehaviour::class.java)
    }
}
