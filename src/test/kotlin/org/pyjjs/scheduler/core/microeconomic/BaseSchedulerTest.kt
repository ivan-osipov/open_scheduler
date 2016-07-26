package org.pyjjs.scheduler.core.microeconomic

import org.junit.After
import org.junit.Before
import org.pyjjs.scheduler.core.api.*
import org.pyjjs.scheduler.core.api.microeconomic.MicroeconomicsSchedulerFactory
import org.pyjjs.scheduler.core.data.HashSetDataSourceImpl
import org.pyjjs.scheduler.core.data.ObservableDataSource
import org.pyjjs.scheduler.core.model.IdentifiableObject
import java.util.*
import java.util.concurrent.Semaphore

abstract class BaseSchedulerTest: PlanRepresentative.StablePlanListener{

    private val factory: SchedulerFactory = MicroeconomicsSchedulerFactory()
    private val dataSource: ObservableDataSource = HashSetDataSourceImpl()
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
        assertPlan(updatedPlan)
        schedulingSemaphore.release()
    }

    fun saveEntities(vararg entities: IdentifiableObject) {
        for (identifiableObject in entities) {
            addEntity(identifiableObject)
        }
        schedulingSemaphore.acquire()
    }

    abstract fun assertPlan(plan: Plan);

    private fun createScheduler(): Scheduler {
        return factory.createScheduler()
    }

    private fun addEntity(entity: IdentifiableObject) {
        scheduler.dataSource.add(entity)
    }

}