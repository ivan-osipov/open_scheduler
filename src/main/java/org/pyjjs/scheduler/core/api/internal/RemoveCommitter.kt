package org.pyjjs.scheduler.core.api.internal

import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.api.PlanChange

class RemoveCommitter : ChangeCommitter() {
    override fun invoke(plan: Plan, change: PlanChange): Plan {
        val removed: Boolean = plan.resourceUsages.remove(change.resourceUsage);
        if(!removed) LOG.warn("Change {0} [remove] skipped. Element not found")
        return plan
    }
}

