package org.pyjjs.scheduler.core.api.impl.actors.task

import akka.actor.ActorContext
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.SourceBasedActorState
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage
import org.pyjjs.scheduler.core.model.Task

import java.util.HashSet

class TaskActorState(actorContext: ActorContext) : SourceBasedActorState<Task>(actorContext) {

    private val offers = HashSet<OfferMessage>()
    private var checkOffersScheduled = false
    var discontent: Double? = null

    fun addOffer(resourcePrice: OfferMessage) {
        offers.add(resourcePrice)
    }

    fun getOffers(): Set<OfferMessage> {
        return offers
    }

    fun checkOffersIsScheduled(): Boolean {
        return checkOffersScheduled
    }

    fun setCheckOffersScheduled(checkOffersScheduled: Boolean) {
        this.checkOffersScheduled = checkOffersScheduled
    }

    override fun copySelf() = this
}
