package org.pyjjs.scheduler.core.api

import org.pyjjs.scheduler.core.data.ObservableDataSource

interface Scheduler {

    fun run(vararg listeners: PlanCompleteListener)

    val isRunning: Boolean

    fun reset()

    val dataSource: ObservableDataSource

    fun addStablePlanListener(listener: PlanRepresentative.StablePlanListener)

    fun removePlanCompleteListener(listener: PlanRepresentative.StablePlanListener)
}