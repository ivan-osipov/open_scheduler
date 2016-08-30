package org.pyjjs.scheduler.core.api

abstract class ObjectiveFunction<in T> {

    abstract fun calculate(source: T, context: Context): Double

    protected fun calculateAbsoluteDifference(source: T, context: Context): Double {
        return Math.abs(calculate(source, context) - idealValue)
    }

    protected val idealValue: Double
        get() = 0.0

}
