package org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.behaviours

import com.google.common.collect.Lists
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.supervisor.TaskSupervisorState
import java.util.*
import kotlin.reflect.KClass

class ResourceAppearedSupervisorBehaviour : Behaviour<TaskSupervisorState, ResourceAppearedMessage>() {
    override fun perform(message: ResourceAppearedMessage) {
        val taskDiscontents = Lists.newArrayList(actorState.getTaskDiscontents())
        //firstly, task with max discontent
        Collections.sort(taskDiscontents) { o1, o2 ->
            val discontent1 = o1.discontent
            val discontent2 = o2.discontent
            Objects.compare<Double>(discontent2, discontent1) { d2, d1 -> if (d2 == null) 1 else if (d1 == null) 1 else d2.compareTo(d1) }
        }

        val tasks = taskDiscontents.map { it.taskActor }.toList()
        sendToAll(tasks, message)
    }

    override fun processMessage(): KClass<ResourceAppearedMessage> {
        return ResourceAppearedMessage::class
    }
}
