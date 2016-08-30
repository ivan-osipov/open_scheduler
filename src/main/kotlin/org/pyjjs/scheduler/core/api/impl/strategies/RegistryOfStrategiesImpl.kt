package org.pyjjs.scheduler.core.api.impl.strategies

import org.pyjjs.scheduler.core.api.RegistryOfStrategies
import org.pyjjs.scheduler.core.api.TaskObjectiveFunction
import org.pyjjs.scheduler.core.api.impl.strategies.jit.TaskJitObjectiveFunction
import java.util.concurrent.ConcurrentHashMap

val DEFAULT_STRATEGY_KEY = "jit"

class RegistryOfStrategiesImpl: RegistryOfStrategies {

    private val registry = ConcurrentHashMap<String, TaskObjectiveFunction>()

    init {
        registry[DEFAULT_STRATEGY_KEY] = TaskJitObjectiveFunction()
    }

    override fun registerStrategy(strategyKey: String, taskObjectiveFunction: TaskObjectiveFunction) {
        check(!DEFAULT_STRATEGY_KEY.equals(strategyKey), {"${DEFAULT_STRATEGY_KEY} is default strategy. Default strategy cannot be added."})
        checkNotNull(strategyKey, {"Strategy key cannot be null"})
        checkNotNull(taskObjectiveFunction, {"Task objective function cannot be null"})
        registry[strategyKey] = taskObjectiveFunction
    }

    override fun unregisterStrategy(strategyKey: String) {
        check(!DEFAULT_STRATEGY_KEY.equals(strategyKey), {"${DEFAULT_STRATEGY_KEY} is default strategy. Default strategy cannot be removed."})
        checkNotNull(strategyKey, {"Strategy key cannot be null"})
        registry.remove(strategyKey)
    }

    override fun fetch(strategyKey: String): TaskObjectiveFunction {
        checkNotNull(strategyKey, {"Strategy key cannot be null"})
        val strategy = registry[strategyKey]
        val checkedStrategy = checkNotNull(strategy, {"You fetch strategy which is not registered"})
        return checkedStrategy
    }
}