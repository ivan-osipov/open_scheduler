package org.pyjjs.scheduler.core.api.impl.actors.common

import akka.actor.ActorContext
import akka.actor.ActorRef
import akka.actor.ActorSystem

abstract class ActorState(val actorContext: ActorContext) {

    var isInitialized = false

    val actorRef: ActorRef
        get() = actorContext.self()

    val actorSystem: ActorSystem
        get() = actorContext.system()

    abstract fun copySelf(): ActorState

}
