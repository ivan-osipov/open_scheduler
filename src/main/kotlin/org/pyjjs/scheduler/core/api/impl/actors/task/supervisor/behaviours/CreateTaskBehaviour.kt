package org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.behaviours

import akka.actor.ActorRef
import akka.actor.Props
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskInitMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.EntityCreatedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActor
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.TaskSupervisorState
import org.pyjjs.scheduler.core.model.Task

class CreateTaskBehaviour : Behaviour<TaskSupervisorState, EntityCreatedMessage>() {

    override fun perform(message: EntityCreatedMessage) {
        val task = message.entity as Task
        val actorState = actorState

        val newTaskActorRef = createTaskActor()
        send(newTaskActorRef, TaskInitMessage(actorRef, task))
        actorState.registerTaskActor(task, newTaskActorRef)

        saveActorState(actorState)
    }

    private fun createTaskActor(): ActorRef {
        return actorState.actorContext.actorOf(Props.create(TaskActor::class.java))
    }

    override fun processMessage(): Class<EntityCreatedMessage> {
        return EntityCreatedMessage::class.java
    }
}
