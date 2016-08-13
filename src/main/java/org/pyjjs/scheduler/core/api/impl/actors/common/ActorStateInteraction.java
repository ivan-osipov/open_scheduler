package org.pyjjs.scheduler.core.api.impl.actors.common;

public interface ActorStateInteraction<T extends ActorState> {

    T getActorState();

    void saveActorState(T actorState);

}
