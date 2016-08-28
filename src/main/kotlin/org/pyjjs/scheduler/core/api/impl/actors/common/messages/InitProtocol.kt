package org.pyjjs.scheduler.core.api.impl.actors.common.messages

import akka.actor.ActorRef
import org.pyjjs.scheduler.core.model.IdentifiableObject
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task

open class InitEntityAgentMessage<out T : IdentifiableObject>(sender: ActorRef, val source: T) : Message(sender)

class TaskInitMessage(sender: ActorRef, source: Task) : InitEntityAgentMessage<Task>(sender, source)

class ResourceInitMessage(sender: ActorRef, source: Resource) : InitEntityAgentMessage<Resource>(sender, source)

class ResourceAppearedMessage(var resourceRef: ActorRef, var resource: Resource, sender: ActorRef) : Message(sender)

class TaskAppearedMessage(var taskRef: ActorRef, var task: Task, sender: ActorRef) : Message(sender)