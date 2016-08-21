package org.pyjjs.scheduler.core.api.impl.actors.common.messages

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.model.ResourceCriteria
import org.pyjjs.scheduler.core.model.schedule_specific.Offer
import org.pyjjs.scheduler.core.placement.Placement
import java.util.*

//PROTOCOL
//task:
class IFindAnyPlacementMessage(taskRef: ActorRef, val taskDescriptor: TaskDescriptor,  var resourceCriteria: ResourceCriteria = ResourceCriteria()) : Message(taskRef)
//resource:
class ResourceNotMatchMessage(resourceRef: ActorRef) : Message(resourceRef)
//or:
class ResourceHasPlacementMessage(resourceRef: ActorRef, var placement: Placement): Message(resourceRef)
//or:
class ResourceHasNotPlacementMessage(resourceRef: ActorRef): Message(resourceRef)
//task:
class OfferAcceptedMessage(taskRef: ActorRef, var offerId: UUID): Message(taskRef)
//resource:
class PlacementReservedMessage(resourceRef: ActorRef, var offerId: UUID): Message(resourceRef)
//or:
class OfferIsNotRelevantMessage(resourceRef: ActorRef, var offerId: UUID): Message(resourceRef)

//DTO
data class TaskDescriptor(val laborContent: Double,
                     val minCapacity: Double,
                     val maxCapacity: Double,
                     val minDuration: Long,
                     val maxDuration: Long,
                     var minStartDate: Long? = null,
                     var deadline: Long? = null) {
    init {
        check(maxCapacity > minCapacity, {"Disturbed limit: maxCapacity > minCapacity"})
        check(maxDuration > minDuration, {"Disturbed limit: maxDuration > minDuration"})
        check(maxCapacity*maxDuration >= laborContent, {"Disturbed limit: maxCapacity*maxDuration >= laborContent"})
        check(minCapacity*minDuration <= laborContent, {"Disturbed limit: minCapacity*minDuration <= laborContent"})
    }
}