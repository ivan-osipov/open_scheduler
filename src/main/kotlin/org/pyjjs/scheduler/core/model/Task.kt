package org.pyjjs.scheduler.core.model

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import java.util.*

class Task(var descriptor: TaskDescriptor) : IdentifiableObject() {

    var result: TaskResult? = null
    var resourceCriteria: ResourceCriteria = ResourceCriteria()

    var successors: Set<Task> = HashSet()
    var predecessors: Set<Task> = HashSet()

    var parents: Set<Task> = HashSet()
    var childs: Set<Task> = HashSet()
}
