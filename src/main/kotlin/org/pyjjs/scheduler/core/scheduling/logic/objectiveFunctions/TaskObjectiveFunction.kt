package org.pyjjs.scheduler.core.scheduling.objectiveFunctions

import org.pyjjs.scheduler.core.model.Task

class TaskObjectiveFunction : ObjectiveFunction<Task>() {

    override fun calculate(source: Task, context: Context): Double {
        val proposedTimeInMills = context.lockup(PROPOSED_LEAD_TIME_IN_MILLS) as Long?
        val deadlineInMills = source.deadline?.time ?: Long.MAX_VALUE
        return (calcDelayTime(proposedTimeInMills, deadlineInMills) + calcLeadTime(proposedTimeInMills, deadlineInMills)).toDouble()
    }

    private fun calcDelayTime(proposedLeadTime: Long?, deadline: Long): Long {
        return Math.max(proposedLeadTime!! - deadline, 0)
    }

    private fun calcLeadTime(proposedLeadTime: Long?, deadline: Long): Long {
        return Math.max(deadline - proposedLeadTime!!, 0)
    }

    companion object {
        val PROPOSED_LEAD_TIME_IN_MILLS = "task.proposed_lead_time.in_mills"
    }
}
