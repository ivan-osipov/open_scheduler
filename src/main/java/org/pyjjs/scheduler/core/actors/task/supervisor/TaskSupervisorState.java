package org.pyjjs.scheduler.core.actors.task.supervisor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.supervisor.SupervisorState;
import org.pyjjs.scheduler.core.model.primary.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskSupervisorState extends SupervisorState<Task> {

    private ActorRef resourceSupervisor;

    public TaskSupervisorState(ActorContext actorContext) {
        super(actorContext);
    }

    public ActorRef getResourceSupervisor() {
        return resourceSupervisor;
    }

    public void setResourceSupervisor(ActorRef resourceSupervisor) {
        this.resourceSupervisor = resourceSupervisor;
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
