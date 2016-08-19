package org.pyjjs.scheduler.core.api.impl.actors.common

import akka.actor.ActorContext

open class SourceBasedActorState<T>(actorContext: ActorContext, var source: T) : ActorState(actorContext) {

    override fun copySelf() = this
}
