package org.pyjjs.scheduler.core.nullsafety

fun min(a: Double?, b: Double?): Double? = when {
    (a == null) -> b
    (b == null) -> a
    (b <= a) -> b
    else -> a
}

fun max(a: Double?, b: Double?): Double? = when {
    (a == null) -> b
    (b == null) -> a
    (b >= a) -> b
    else -> a
}
