package org.pyjjs.scheduler.core.api.impl.actors.common.messages

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.strategies.DEFAULT_STRATEGY_KEY
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.ResourceCriteria
import org.pyjjs.scheduler.core.placement.Offer
import org.pyjjs.scheduler.core.placement.Placement
import java.util.*

//PROTOCOL
//task:
class IFindAnyPlacementMessage(taskRef: ActorRef, val taskDescriptor: TaskDescriptor,  var resourceCriteria: ResourceCriteria = ResourceCriteria()) : Message(taskRef)
//resource:
open class ResourceHasPlacementMessage(resourceRef: ActorRef, val resource: Resource, var offer: Offer): Message(resourceRef)
//is:
class ResourceHasFullPlacementMessage(resourceRef: ActorRef, resource: Resource, offer: Offer): ResourceHasPlacementMessage(resourceRef, resource, offer)
//or:
class ResourceHasPartiallyPlacementMessage(resourceRef: ActorRef, resource: Resource, offer: Offer): ResourceHasPlacementMessage(resourceRef, resource, offer)
//or:
class ResourceHasNotPlacementMessage(resourceRef: ActorRef, val rejectionReason: RejectionReason): Message(resourceRef)
//task:
class OfferAcceptedMessage(taskRef: ActorRef, var offerId: UUID): Message(taskRef)
//resource:
class PlacementReservedMessage(resourceRef: ActorRef, var offerId: UUID): Message(resourceRef)
//or:
class OfferIsNotRelevantMessage(resourceRef: ActorRef, var offerId: UUID): Message(resourceRef)

//DTO
data class TaskDescriptor @JvmOverloads constructor(
                     val name: String = "defaultName",
                     val laborContent: Double,
                     val minCapacity: Double,
                     val maxCapacity: Double,
                     val minDuration: Long,
                     val maxDuration: Long,
                     var minStartDate: Long? = null,
                     var deadline: Long? = null,
                     var strategy: String = DEFAULT_STRATEGY_KEY) {
    init {
        check(maxCapacity > minCapacity, {"Disturbed limit: maxCapacity > minCapacity"})
        check(maxDuration > minDuration, {"Disturbed limit: maxDuration > minDuration"})
        check(maxCapacity*maxDuration >= laborContent, {"Disturbed limit: maxCapacity*maxDuration >= laborContent"})
        check(minCapacity*minDuration <= laborContent, {"Disturbed limit: minCapacity*minDuration <= laborContent"})
    }
}

enum class RejectionReason { HAS_NOT_FREE_TIME,  NOT_MATCH }