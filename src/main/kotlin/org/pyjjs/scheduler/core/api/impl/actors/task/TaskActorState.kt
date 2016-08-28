package org.pyjjs.scheduler.core.api.impl.actors.task

import akka.actor.ActorContext
import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.SourceBasedActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasFullPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage
import org.pyjjs.scheduler.core.model.Task
import org.pyjjs.scheduler.core.placement.Placement
import java.util.*

class TaskActorState(actorContext: ActorContext) : SourceBasedActorState<Task>(actorContext) {

    enum class Status { UNPLACED, WAIT_OFFER, WAIT_RESERVING, PLACED }

    private val offers = HashSet<OfferMessage>()
    private var checkOffersScheduled = false
    var discontent: Double? = null
    var status: Status = Status.UNPLACED
    var placementMessageQueue = PriorityQueue<ResourceHasFullPlacementMessage>()
    var offersById = HashMap<UUID, ResourceHasFullPlacementMessage>()

    //messaging info
    private val notMatchedResources = HashSet<ActorRef>()
    private val resourcesWithoutFreeTimes = HashSet<ActorRef>()

    fun addOffer(resourcePrice: OfferMessage) {
        offers.add(resourcePrice)
    }

    fun getOffers(): Set<OfferMessage> {
        return offers
    }

    fun addNotMatchedResource(ref: ActorRef) {
        notMatchedResources.add(ref)
    }

    fun addResourceWithoutFreeTimes(ref: ActorRef) {
        resourcesWithoutFreeTimes.add(ref)
    }

    fun checkOffersIsScheduled(): Boolean {
        return checkOffersScheduled
    }

    fun setCheckOffersScheduled(checkOffersScheduled: Boolean) {
        this.checkOffersScheduled = checkOffersScheduled
    }

    override fun copySelf() = this
}
