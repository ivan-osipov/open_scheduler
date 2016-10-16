package org.pyjjs.scheduler.core.api.impl.strategies.jit

import org.pyjjs.scheduler.core.api.TaskObjectiveFunction
import org.pyjjs.scheduler.core.model.Task
import org.pyjjs.scheduler.core.api.Context
import org.pyjjs.scheduler.core.api.PROPOSED_LEAD_TIME

class TaskJitObjectiveFunction : TaskObjectiveFunction() {

    override fun calculate(source: Task, context: Context): Double {
        val proposedTime = context.lockup(PROPOSED_LEAD_TIME) as Long
        val deadline = source.descriptor.deadline ?: Long.MAX_VALUE
        return (deadline - proposedTime).toDouble()
    }

}
