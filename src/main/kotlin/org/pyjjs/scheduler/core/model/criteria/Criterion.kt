package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.model.criteria.operations.bool.BoolOperation

abstract class Criterion<in T: Comparable<T>> (
        var fieldName: String,
        val operation: BoolOperation<T>,
        val value: Comparable<T>
)