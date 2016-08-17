package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.model.criteria.operations.bool.BoolOperation

class StrictCriterion<in T: Comparable<T>>(value: T, fieldName: String, operation: BoolOperation<T>):
        Criterion<T>(value, operation, fieldName)