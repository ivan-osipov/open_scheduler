package org.pyjjs.scheduler.core.actors.system;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.ActorState;

public class ModificationControllerState extends ActorState {

    private ActorRef taskSupervisor;
    private ActorRef resourceSupervisor;

    public ModificationControllerState(ActorContext actorContext) {
        super(actorContext);
    }

    public void setTaskSupervisor(ActorRef taskSupervisor) {
        this.taskSupervisor = taskSupervisor;
    }

    public void setResourceSupervisor(ActorRef resourceSupervisor) {
        this.resourceSupervisor = resourceSupervisor;
    }

    public ActorRef getTaskSupervisor() {
        return taskSupervisor;
    }

    public ActorRef getResourceSupervisor() {
        return resourceSupervisor;
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
