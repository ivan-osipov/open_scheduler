package org.pyjjs.scheduler.core.api.impl.actors.resource.messages

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.model.Resource

class OfferMessage(sender: ActorRef) : Message(sender) {

    var placingPrice: Double = 0.0
    var resource: Resource? = null
}
