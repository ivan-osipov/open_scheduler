package org.pyjjs.scheduler.core.logic.objectiveFunctions;

import org.pyjjs.scheduler.core.model.Task;

public class TaskObjectiveFunction extends ObjectiveFunction<Task> {
    public static final String PROPOSED_LEAD_TIME_IN_MILLS = "task.proposed_lead_time.in_mills";

    @Override
    public double calculate(Task source, Context context) {
        Long proposedTimeInMills = (Long) context.lockup(PROPOSED_LEAD_TIME_IN_MILLS);
        Long deadlineInMills = source.getDeadline().getTime();
        return calcDelayTime(proposedTimeInMills, deadlineInMills) + calcLeadTime(proposedTimeInMills, deadlineInMills);
    }

    private long calcDelayTime(Long proposedLeadTime, Long deadline) {
        return Math.max(proposedLeadTime-deadline, 0);
    }

    private long calcLeadTime(Long proposedLeadTime, Long deadline) {
        return Math.max(deadline-proposedLeadTime, 0);
    }
}
