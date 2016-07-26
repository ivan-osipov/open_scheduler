package org.pyjjs.scheduler.core.scheduling.objectiveFunctions

abstract class ObjectiveFunction<T> {

    abstract fun calculate(source: T, context: Context): Double

    fun calculateAbsoluteDifference(source: T, context: Context): Double {
        return Math.abs(calculate(source, context) - idealValue)
    }

    val idealValue: Double
        get() = 0.0

}
