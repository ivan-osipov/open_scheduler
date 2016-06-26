package org.pyjjs.scheduler.core.actors.resource.supervisor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.supervisor.SupervisorState;
import org.pyjjs.scheduler.core.model.primary.Resource;

public class ResourceSupervisorState extends SupervisorState<Resource> {

    private ActorRef taskSupervisor;

    public ResourceSupervisorState(ActorContext actorContext) {
        super(actorContext);
    }

    public ActorRef getTaskSupervisor() {
        return taskSupervisor;
    }

    public void setTaskSupervisor(ActorRef taskSupervisor) {
        this.taskSupervisor = taskSupervisor;
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
