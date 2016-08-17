package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.model.criteria.operations.bool.BoolOperation

abstract class Criterion<in T: Comparable<T>> (
        val value: Comparable<T>,
        val operation: BoolOperation<T>,
        var fieldName: String
)