package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import org.pyjjs.scheduler.core.common.groovy.GroovyScriptExecutor
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import java.util.*

private val RESOURCE_VARIABLE_NAME = "r"

infix fun <T: Comparable<T>> Resource.match(criteria: Collection<StrictCriterion<T>>): Boolean {
    val script = createGroovyScript(criteria)
    val variables: Map<String?, Any> = mapOf(RESOURCE_VARIABLE_NAME to this)
    val scriptResultList = GroovyScriptExecutor.invokeScript(script, List::class.java, variables)
    return !scriptResultList.contains(false)
}

infix fun Resource.match(taskDescriptor: TaskDescriptor): Boolean {
    val maxAvailableCapacity = this.timeSheet.resourceAvailabilityTable.maxAvailableCapacity
    val minAvailableCapacity = this.timeSheet.resourceAvailabilityTable.minAvailableCapacity
    maxAvailableCapacity ?: return false
    minAvailableCapacity ?: return false
    return maxAvailableCapacity >= taskDescriptor.minCapacity &&
            minAvailableCapacity <= taskDescriptor.maxCapacity
}

fun <T: Comparable<T>> createGroovyScript(criteria: Collection<StrictCriterion<T>>): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[")
    val stringJoiner = StringJoiner(",")
    criteria.forEach {
        stringJoiner.add(createEvaluation(it))
    }
    stringBuilder.append(stringJoiner.toString())
    stringBuilder.append("]")
    return stringBuilder.toString()
}

fun <T: Comparable<T>> createEvaluation(criterion: StrictCriterion<T>): CharSequence? {
    return StringJoiner(" ")
            .add(RESOURCE_VARIABLE_NAME + "."+ criterion.fieldName)
            .add(criterion.operation.toString())
            .add(criterion.value.toString()).toString()
}
