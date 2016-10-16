package org.pyjjs.scheduler.core.api.impl.actors.common.messages

import akka.actor.ActorRef

//PROTOCOL
//SchedulingController:
class FindBetterMessage(sender: ActorRef) : Message(sender)
//task:
class IFindBetterReplacement(sender: ActorRef): Message(sender)
//resource:
