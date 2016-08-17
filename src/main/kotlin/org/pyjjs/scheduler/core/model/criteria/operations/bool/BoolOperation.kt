package org.pyjjs.scheduler.core.model.criteria.operations.bool

interface BoolOperation<in T: Comparable<T>> {
    fun invoke(a: T, b: T): Boolean
}