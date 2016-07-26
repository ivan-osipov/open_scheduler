package org.pyjjs.scheduler.core.scheduling.objectiveFunctions

interface Context {

    fun lockup(key: String): Any

    fun put(key: String, value: Any)

}
