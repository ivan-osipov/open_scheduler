package org.pyjjs.scheduler.core.api.impl.actors.common

import akka.actor.ActorContext
import org.pyjjs.scheduler.core.model.IdentifiableObject

open class SourceBasedActorState<T: IdentifiableObject>(actorContext: ActorContext) : ActorState(actorContext) {
    lateinit var source: T
    override fun copySelf() = this
}
