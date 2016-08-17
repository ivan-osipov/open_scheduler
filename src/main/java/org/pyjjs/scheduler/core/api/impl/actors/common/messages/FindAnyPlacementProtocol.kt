package org.pyjjs.scheduler.core.api.impl.actors.common.messages

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.model.ResourceCriteria
import org.pyjjs.scheduler.core.model.schedule_specific.Offer
import java.util.*

//task:
class IFindAnyPlacementMessage(taskRef: ActorRef, var resourceCriteria: ResourceCriteria = ResourceCriteria()) : Message(taskRef)
//resource:
class ResourceNotMatchMessage(resourceRef: ActorRef) : Message(resourceRef)
//or:
class ResourceHasPlacementMessage(resourceRef: ActorRef, var offer: Offer): Message(resourceRef)
//or:
class ResourceHasNotPlacementMessage(resourceRef: ActorRef): Message(resourceRef)
//task:
class OfferAcceptedMessage(taskRef: ActorRef, var offerId: UUID): Message(taskRef)
//resource:
class PlacementReservedMessage(resourceRef: ActorRef, var offerId: UUID): Message(resourceRef)
//or:
class OfferIsNotRelevantMessage(resourceRef: ActorRef, var offerId: UUID): Message(resourceRef)