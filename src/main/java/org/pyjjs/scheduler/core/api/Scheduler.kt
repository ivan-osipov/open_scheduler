package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.data.ObservableDataSource

interface Scheduler: PlanRepresentative{

    fun run()

    val isRunning: Boolean

    fun reset()

    val dataSource: ObservableDataSource
}
