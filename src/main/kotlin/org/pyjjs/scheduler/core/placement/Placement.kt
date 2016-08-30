package org.pyjjs.scheduler.core.placement

data class Placement(val type: Type, val offer: Offer? = null) {
    enum class Type { FULL, PARTIAL, IMPOSSIBLY }
}