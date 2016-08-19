package org.pyjjs.scheduler.core.api.impl.actors.common.supervisor

import akka.actor.ActorContext
import akka.actor.ActorRef
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState
import org.pyjjs.scheduler.core.model.IdentifiableObject

import java.util.HashMap

abstract class SupervisorState<in T : IdentifiableObject>(actorContext: ActorContext) : ActorState(actorContext) {

    private val actorsBySource = HashMap<T, ActorRef>()

    open fun registerTaskActor(entity: T, actorRef: ActorRef) {
        actorsBySource.put(entity, actorRef)
    }

    fun unregisterTaskActor(entity: T) {
        actorsBySource.remove(entity)
    }

    fun findBySource(entity: T): ActorRef? {
        return actorsBySource[entity]
    }

}
