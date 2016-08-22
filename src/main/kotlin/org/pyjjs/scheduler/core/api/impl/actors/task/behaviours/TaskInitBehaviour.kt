package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskInitMessage

class TaskInitBehaviour : TaskBehaviour<TaskInitMessage>() {

    override fun perform(message: TaskInitMessage) {
        val taskActorState = actorState
        taskActorState.source = message.source
        taskActorState.isInitialized = true
        saveActorState(taskActorState)
    }

    override fun processMessage(): Class<TaskInitMessage> {
        return TaskInitMessage::class.java
    }
}
