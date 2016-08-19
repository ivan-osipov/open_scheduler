package org.pyjjs.scheduler.core.api.impl.actors.resource

import akka.actor.ActorContext
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.api.impl.actors.common.SourceBasedActorState
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.JitPlacementFinder
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.PlacementFinder
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.TimeLine
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.TimeSheet
import org.pyjjs.scheduler.core.model.Resource

import java.util.HashSet

class ResourceActorState (actorContext: ActorContext, source: Resource) : SourceBasedActorState<Resource>(actorContext, source) {

    internal enum class Status {
        CREATED, RECEIVE_REQUEST, DONE
    }

    var placingPrice: Double = 0.0

    var timeSheet = TimeSheet()

    private val placementFinder = JitPlacementFinder()

    override fun copySelf(): ResourceActorState {
        return this
    }


}
