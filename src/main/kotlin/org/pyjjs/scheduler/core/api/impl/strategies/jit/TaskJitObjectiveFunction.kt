package org.pyjjs.scheduler.core.api.impl.strategies.jit

import org.pyjjs.scheduler.core.api.TaskObjectiveFunction
import org.pyjjs.scheduler.core.model.Task
import org.pyjjs.scheduler.core.api.Context
import org.pyjjs.scheduler.core.api.PROPOSED_LEAD_TIME

class TaskJitObjectiveFunction : TaskObjectiveFunction() {

    override fun calculate(source: Task, context: Context): Double {
        val proposedTimeInMills = context.lockup(PROPOSED_LEAD_TIME) as Long
        val deadline = source.descriptor.deadline ?: Long.MAX_VALUE
        return (calcDelayTime(proposedTimeInMills, deadline) + calcLeadTime(proposedTimeInMills, deadline)).toDouble()
    }

    private fun calcDelayTime(proposedLeadTime: Long, deadline: Long): Long {
        return Math.max(proposedLeadTime - deadline, 0)
    }

    private fun calcLeadTime(proposedLeadTime: Long, deadline: Long): Long {
        return Math.max(deadline - proposedLeadTime, 0)
    }
}
