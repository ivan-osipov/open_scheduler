package org.pyjjs.scheduler.core.api

interface RegistryOfStrategies {

    fun registerStrategy(strategyKey: String, taskObjectiveFunction: TaskObjectiveFunction)

    fun unregisterStrategy(strategyKey: String)

    fun fetch(strategyKey: String): TaskObjectiveFunction

}