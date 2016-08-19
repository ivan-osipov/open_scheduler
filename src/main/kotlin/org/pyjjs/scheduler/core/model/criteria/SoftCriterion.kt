package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.model.criteria.operations.bool.BoolOperation

class SoftCriterion<in T: Comparable<T>>(fieldName: String, operation: BoolOperation<T>, value: T, var weight: Double): Criterion<T>(fieldName, operation, value)