package org.pyjjs.scheduler.core.model

import java.util.Date
import java.util.HashSet

class Task : IdentifiableObject() {

    var duration: Long? = null
    var minStartDate: Date? = null
    var deadline: Date? = null
    var taskResult: TaskResult? = null

    var successors: Set<Task> = HashSet()
    var predecessors: Set<Task> = HashSet()

    var parents: Set<Task> = HashSet()
    var childs: Set<Task> = HashSet()
}
