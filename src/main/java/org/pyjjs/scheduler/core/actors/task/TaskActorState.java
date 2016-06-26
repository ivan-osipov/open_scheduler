package org.pyjjs.scheduler.core.actors.task;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.SourceBasedActorState;
import org.pyjjs.scheduler.core.model.primary.Task;

public class TaskActorState extends SourceBasedActorState<Task> {

    public TaskActorState(ActorContext actorContext) {
        super(actorContext);
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
