package org.pyjjs.scheduler.core.api.impl.actors.common.messages

import akka.actor.ActorRef

open class Message @JvmOverloads constructor(val sender: ActorRef? = ActorRef.noSender())
