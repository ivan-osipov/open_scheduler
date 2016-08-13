package org.pyjjs.scheduler.core.microeconomic

import org.junit.After
import org.junit.Before
import org.pyjjs.scheduler.core.api.*
import org.pyjjs.scheduler.core.api.impl.MicroeconomicsSchedulerFactory
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.data.HashSetDataSourceImpl
import org.pyjjs.scheduler.core.data.ObservableDataSource
import org.pyjjs.scheduler.core.model.IdentifiableObject
import java.util.*
import java.util.concurrent.Semaphore

abstract class BaseSchedulerTest: PlanRepresentative.StablePlanListener {

    private val scheduler: Scheduler = createScheduler()
    private val schedulingSemaphore: Semaphore = Semaphore(1)

    @Before
    fun startUp() {
        scheduler.addStablePlanListener(this)
        scheduler.run()
        schedulingSemaphore.acquire()
    }

    @After
    fun tearDown() {
        scheduler.reset()
        schedulingSemaphore.release()
    }

    override fun afterPlanUpdate(updatedPlan: Plan, lastAppliedChanges: SortedSet<PlanChange>) {
        assertPlan(updatedPlan, lastAppliedChanges)
        schedulingSemaphore.release()
    }

    fun saveEntities(vararg entities: IdentifiableObject) {
        for (identifiableObject in entities) {
            addEntity(identifiableObject)
        }
        schedulingSemaphore.acquire()
    }

    private fun addEntity(entity: IdentifiableObject) {
        scheduler.dataSource.add(entity)
    }

    private fun createScheduler(): Scheduler {
        return MicroeconomicsSchedulerFactory().createScheduler()
    }

    abstract fun assertPlan(plan: Plan, lastAppliedChanges: SortedSet<PlanChange>)

}