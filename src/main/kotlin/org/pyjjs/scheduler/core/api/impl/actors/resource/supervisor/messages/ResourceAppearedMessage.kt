package org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.messages

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.model.Resource

class ResourceAppearedMessage(var resourceRef: ActorRef, var resource: Resource, sender: ActorRef) : Message(sender)
