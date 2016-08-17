package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.model.Resource
import java.beans.PropertyDescriptor

infix fun <T: Comparable<T>> Resource.match(criteria: Collection<StrictCriterion<T>>): Boolean {
    criteria.forEach {
        @Suppress("UNCHECKED_CAST")
        val resourceValue: T = PropertyDescriptor(it.fieldName, Resource::class.java).readMethod.invoke(this) as T
        @Suppress("UNCHECKED_CAST")
        val criterionValue: T = it.value as T

        if (resourceValue !is Comparable<*>) {
            throw IllegalStateException("Resource tried to match incomparable field ${it.fieldName}")
        }
        if(! it.operation.invoke(criterionValue, resourceValue) ) {
            return false
        }
    }
    return true
}