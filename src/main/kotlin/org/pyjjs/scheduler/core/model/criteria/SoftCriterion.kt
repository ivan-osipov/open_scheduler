package org.pyjjs.scheduler.core.model.criteria

import org.pyjjs.scheduler.core.model.criteria.operations.bool.BoolOperation

class SoftCriterion<in T: Comparable<T>>(value: T, operation: BoolOperation<T>, fieldName: String, var weight: Double): Criterion<T>(value, operation, fieldName)