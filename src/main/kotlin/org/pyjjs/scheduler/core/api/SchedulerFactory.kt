package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.data.ObservableDataSource

interface SchedulerFactory {

    fun createScheduler(): Scheduler

    fun createScheduler(dataSource: ObservableDataSource): Scheduler
}
