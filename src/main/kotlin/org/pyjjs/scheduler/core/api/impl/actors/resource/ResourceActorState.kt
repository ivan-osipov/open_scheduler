package org.pyjjs.scheduler.core.api.impl.actors.resource

import akka.actor.ActorContext
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.SourceBasedActorState
import org.pyjjs.scheduler.core.placement.PlacementFinderImpl
import org.pyjjs.scheduler.core.placement.PlacementFinder
import org.pyjjs.scheduler.core.placement.time.TimeSheet
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.placement.Offer
import java.util.*

class ResourceActorState (actorContext: ActorContext,
                          var placementFinder: PlacementFinder = PlacementFinderImpl()) : SourceBasedActorState<Resource>(actorContext) {
    internal enum class Status {
        CREATED, RECEIVE_REQUEST, DONE
    }

    var placingPrice: Double = 0.0

    var offersById = HashMap<UUID, Offer>()
    var timeSheetsByOfferId = HashMap<UUID, TimeSheet>()

    lateinit var timeSheet: TimeSheet

    override fun copySelf(): ResourceActorState {
        return this
    }


}
