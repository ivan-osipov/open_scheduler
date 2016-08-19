package org.pyjjs.scheduler.core.common.groovy

import groovy.lang.Binding
import groovy.lang.GroovyShell

object GroovyScriptExecutor {

    private val binding = Binding()
    private val shell = GroovyShell(binding)

    fun <T> invokeScript(script: String, resultType: Class<T>, variables: Map<String?, Any>?) : T{
        binding.variables.clear()
        if(variables != null) {
            for ((key, value) in variables) {
                binding.setVariable(key, value)
            }
        }
        return resultType.cast(shell.evaluate(script))
    }
}