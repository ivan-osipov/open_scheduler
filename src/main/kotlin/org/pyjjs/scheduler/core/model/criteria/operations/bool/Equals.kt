package org.pyjjs.scheduler.core.model.criteria.operations.bool

class Equals<in T: Comparable<T>>: BoolOperation<T> {
    override fun invoke(a: T, b: T): Boolean {
        return a == b
    }

    override fun toString(): String {
        return "=="
    }
}