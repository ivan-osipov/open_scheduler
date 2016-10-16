package org.pyjjs.scheduler.core.api.impl.utils

import org.pyjjs.scheduler.core.model.IdentifiableObject
import java.util.*

fun <T : IdentifiableObject> T.withId() : T {
    this.id = UUID.randomUUID()
    return this
}