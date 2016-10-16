package org.pyjjs.scheduler.core.api.impl.actors.system.messages

import akka.actor.ActorRef
import com.google.common.collect.Sets
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.api.impl.utils.TIMESTAMP_COMPARATOR
import java.util.*

class PlanUpdatedMessage(sender: ActorRef, val planChanges: SortedSet<PlanChange> = Sets.newTreeSet<PlanChange>(TIMESTAMP_COMPARATOR)) : Message(sender)

class CheckNewChanges: Message()