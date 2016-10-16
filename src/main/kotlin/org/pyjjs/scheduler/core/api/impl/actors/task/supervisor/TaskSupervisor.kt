package org.pyjjs.scheduler.core.api.impl.actors.task.supervisor

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.BehaviourBasedActor
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.behaviours.CreateTaskBehaviour
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.behaviours.ResourceAppearedSupervisorBehaviour
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TaskSupervisor : BehaviourBasedActor<TaskSupervisorState>() {

    init {
        val taskSupervisorState = TaskSupervisorState(context)
        updateActorState(taskSupervisorState)
    }

    override fun init() {
        fillBehaviours()
        LOG.info("Task supervisor is initialized")
    }

    private fun fillBehaviours() {
        addBehaviour(CreateTaskBehaviour::class)
        addBehaviour(ResourceAppearedSupervisorBehaviour::class)
    }
}
