package org.pyjjs.scheduler.core.api.internal

import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.api.PlanChange

class UpdateCommitter : ChangeCommitter() {
    override fun invoke(plan: Plan, change: PlanChange): Plan {
        plan.resourceUsages.remove(change.resourceUsage)
        plan.resourceUsages.add(change.resourceUsage)
        return plan
    }

}

