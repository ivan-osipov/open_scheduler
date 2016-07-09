package org.pyjjs.scheduler.core.actors.task.supervisor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import com.google.common.collect.Sets;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.supervisor.SupervisorState;
import org.pyjjs.scheduler.core.model.Task;

import java.util.*;

public class TaskSupervisorState extends SupervisorState<Task> {

    private ActorRef resourceSupervisor;
    private Set<TaskDiscontent> taskDiscontents;

    public TaskSupervisorState(ActorContext actorContext) {
        super(actorContext);
        taskDiscontents = Sets.newHashSet();
    }

    @Override
    public void registerTaskActor(Task entity, ActorRef actorRef) {
        super.registerTaskActor(entity, actorRef);
        taskDiscontents.add(new TaskDiscontent(actorRef, null));
    }

    public ActorRef getResourceSupervisor() {
        return resourceSupervisor;
    }

    public void setResourceSupervisor(ActorRef resourceSupervisor) {
        this.resourceSupervisor = resourceSupervisor;
    }

    public Set<TaskDiscontent> getTaskDiscontents() {
        return taskDiscontents;
    }

    public void setTaskDiscontents(Set<TaskDiscontent> taskDiscontents) {
        this.taskDiscontents = taskDiscontents;
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }

    public class TaskDiscontent {

        private ActorRef taskActor;
        private Double discontent;

        public TaskDiscontent(ActorRef taskActor, Double discontent) {
            this.taskActor = taskActor;
            this.discontent = discontent;
        }

        public ActorRef getTaskActor() {
            return taskActor;
        }

        public void setTaskActor(ActorRef taskActor) {
            this.taskActor = taskActor;
        }

        public Double getDiscontent() {
            return discontent;
        }

        public void setDiscontent(Double discontent) {
            this.discontent = discontent;
        }
    }
}
