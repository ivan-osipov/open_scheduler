package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.model.criteria.operations.bool.BoolOperation

class StrictCriterion<in T: Comparable<T>>(fieldName: String, operation: BoolOperation<T>, value: T):
        Criterion<T>(fieldName, operation, value)