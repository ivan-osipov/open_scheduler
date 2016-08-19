package org.pyjjs.scheduler.core.api.impl.actors.common

interface ActorStateInteraction<T : ActorState> {

    fun getActorState(): T

    fun saveActorState(actorState: T)

}
