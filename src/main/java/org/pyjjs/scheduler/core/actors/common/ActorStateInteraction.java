package org.pyjjs.scheduler.core.actors.common;

public interface ActorStateInteraction<T extends ActorState> {

    T getActorState();

    void saveActorState(T actorState);

}
