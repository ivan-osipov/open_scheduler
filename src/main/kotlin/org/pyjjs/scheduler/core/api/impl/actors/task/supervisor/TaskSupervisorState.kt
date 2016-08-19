package org.pyjjs.scheduler.core.api.impl.actors.task.supervisor

import akka.actor.ActorContext
import akka.actor.ActorRef
import com.google.common.collect.Sets
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.supervisor.SupervisorState
import org.pyjjs.scheduler.core.model.Task

import java.util.*

class TaskSupervisorState(actorContext: ActorContext) : SupervisorState<Task>(actorContext) {

    var resourceSupervisor: ActorRef? = null

    private var taskDiscontents: MutableSet<TaskDiscontent> = Sets.newHashSet<TaskDiscontent>()

    override fun registerTaskActor(entity: Task, actorRef: ActorRef) {
        super.registerTaskActor(entity, actorRef)
        taskDiscontents.add(TaskDiscontent(actorRef, null))
    }

    fun getTaskDiscontents(): Set<TaskDiscontent> {
        return taskDiscontents
    }

    fun setTaskDiscontents(taskDiscontents: MutableSet<TaskDiscontent>) {
        this.taskDiscontents = taskDiscontents
    }

    override fun copySelf() = this

    inner class TaskDiscontent(var taskActor: ActorRef, var discontent: Double?)
}
