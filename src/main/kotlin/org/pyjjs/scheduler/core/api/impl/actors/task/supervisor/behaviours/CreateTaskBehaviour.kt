package org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.behaviours

import akka.actor.ActorRef
import akka.actor.Props
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskInitMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.EntityCreatedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActor
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.TaskSupervisorState
import org.pyjjs.scheduler.core.model.Task

class CreateTaskBehaviour : Behaviour<TaskSupervisorState, EntityCreatedMessage>() {

    override fun perform(message: EntityCreatedMessage) {
        val task = message.entity as Task

        val newTaskActorRef = createTaskActor(task)
        actorState.registerTaskActor(task, newTaskActorRef)
        saveActorState(actorState)

        notifyAboutTaskAppeared(task, newTaskActorRef)
    }

    private fun notifyAboutTaskAppeared(task: Task, taskRef: ActorRef) {
        publishToEventStream(TaskAppearedMessage(taskRef, task, actorRef))
    }

    private fun createTaskActor(task: Task): ActorRef {
        return actorState.actorContext.actorOf(Props.create(TaskActor::class.java, task))
    }

    override fun processMessage(): Class<EntityCreatedMessage> {
        return EntityCreatedMessage::class.java
    }
}
