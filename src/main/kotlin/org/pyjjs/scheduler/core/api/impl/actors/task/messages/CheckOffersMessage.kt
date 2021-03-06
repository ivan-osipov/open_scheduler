package org.pyjjs.scheduler.core.api.impl.actors.task.messages

import akka.actor.ActorRef
import com.google.common.collect.Lists
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message

class CheckOffersMessage(sender: ActorRef) : Message(sender)
