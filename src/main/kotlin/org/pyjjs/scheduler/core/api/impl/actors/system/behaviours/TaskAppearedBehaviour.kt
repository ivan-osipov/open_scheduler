package org.pyjjs.scheduler.core.api.impl.actors.system.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.SchedulingControllerState

class TaskAppearedBehaviour: Behaviour<SchedulingControllerState, TaskAppearedMessage>() {
    override fun perform(message: TaskAppearedMessage) {
        actorState.unplacedTasks.add(message.taskRef)
        actorState.tasks.put(message.task, message.taskRef)
        saveActorState(actorState)
    }

    override fun processMessage(): Class<out TaskAppearedMessage> {
        return TaskAppearedMessage::class.java
    }


}
