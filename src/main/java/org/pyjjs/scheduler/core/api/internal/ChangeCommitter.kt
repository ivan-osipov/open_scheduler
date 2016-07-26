package org.pyjjs.scheduler.core.api.internal

import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.api.PlanChange
import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class  ChangeCommitter {

    protected val LOG: Logger = LoggerFactory.getLogger(ChangeCommitter::class.java)

    fun commit(plan: Plan, change: PlanChange): Plan {
        plan.lastUpdate = Math.max(plan.lastUpdate, change.timestamp)
        return invoke(plan, change)
    }

    abstract fun invoke(plan: Plan, change: PlanChange): Plan;
}

