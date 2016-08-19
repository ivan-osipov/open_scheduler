package org.pyjjs.scheduler.core.api.impl.actors.system.messages

import com.google.common.collect.Sets
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.api.impl.utils.Comparators
import java.util.*

class PlanUpdatedMessage : Message() {
    val planChanges: SortedSet<PlanChange> = Sets.newTreeSet<PlanChange>(Comparators.TIMESTAMP_COMPARATOR)
}

class CheckNewChanges: Message() {
    companion object Instance {
        val INSTANCE = CheckNewChanges()
    }
}