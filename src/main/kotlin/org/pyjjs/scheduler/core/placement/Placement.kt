package org.pyjjs.scheduler.core.placement

import org.pyjjs.scheduler.core.model.schedule_specific.Offer

data class Placement(val type: Type, val offer: Offer? = null) {
    enum class Type { FULL, PARTIAL, IMPOSSIBLY }
}