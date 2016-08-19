package org.pyjjs.scheduler.core.api.impl.changes

import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange

class InsertCommitter : ChangeCommitter() {

    override fun invoke(plan: Plan, change: PlanChange): Plan {
        plan.resourceUsages.add(change.resourceUsage)
        return plan
    }

}

