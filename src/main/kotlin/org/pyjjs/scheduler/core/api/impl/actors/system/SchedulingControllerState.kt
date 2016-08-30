package org.pyjjs.scheduler.core.api.impl.actors.system

import akka.actor.ActorContext
import akka.actor.ActorRef
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import org.pyjjs.scheduler.core.api.RegistryOfStrategies
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange
import org.pyjjs.scheduler.core.api.SchedulingListener
import org.pyjjs.scheduler.core.api.impl.utils.Comparators
import org.pyjjs.scheduler.core.model.Task
import java.util.*

class SchedulingControllerState(actorContext: ActorContext, val registryOfStrategies: RegistryOfStrategies) : ActorState(actorContext) {

    var schedulingListeners: MutableSet<SchedulingListener> = HashSet()

    var planChanges: SortedSet<PlanChange> = TreeSet(Comparators.TIMESTAMP_COMPARATOR)

    val tasks: BiMap<Task, ActorRef> = HashBiMap.create()

    val unplacedTasks: MutableSet<ActorRef> = HashSet()

    val discontentsByTaskActors: MutableMap<ActorRef, Double> = HashMap()

    private var checkOffersAreScheduled = false

    override fun copySelf() = this

    fun checkOffersAreScheduled(): Boolean {
        return checkOffersAreScheduled
    }

    fun setCheckOffersAreScheduled(checkOffersAreScheduled: Boolean) {
        this.checkOffersAreScheduled = checkOffersAreScheduled
    }
}
