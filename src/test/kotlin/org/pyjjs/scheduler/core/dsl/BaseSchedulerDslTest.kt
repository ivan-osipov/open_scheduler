package org.pyjjs.scheduler.core.dsl

import org.pyjjs.scheduler.core.BaseSchedulerTest
import org.pyjjs.scheduler.core.api.Plan
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.model.IdentifiableObject
import java.util.*

open class BaseSchedulerDslTest: BaseSchedulerTest() {

    private var data: Set<IdentifiableObject> = HashSet()
    protected var asserts: (plan: Plan, lastAppliedChanges: SortedSet<PlanChange>) -> Unit = {p,c ->}

    protected fun dataSource(dataSourceSupplier: () -> Set<IdentifiableObject>) {
        data = dataSourceSupplier.invoke()
    }

    protected fun assertSchedule(asserts: (plan: Plan, lastAppliedChanges: SortedSet<PlanChange>) -> Unit) {
        this.asserts = asserts
        saveEntities(data)
    }

    override fun assertPlan(plan: Plan, lastAppliedChanges: SortedSet<PlanChange>) {
        asserts.invoke(plan, lastAppliedChanges)
    }
}
