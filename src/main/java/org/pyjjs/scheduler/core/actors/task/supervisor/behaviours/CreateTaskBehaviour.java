package org.pyjjs.scheduler.core.actors.task.supervisor.behaviours;

import akka.actor.ActorRef;
import akka.actor.Props;
import org.pyjjs.scheduler.core.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActor;
import org.pyjjs.scheduler.core.actors.task.supervisor.TaskSupervisorState;
import org.pyjjs.scheduler.core.actors.task.supervisor.messages.TaskInitMessage;
import org.pyjjs.scheduler.core.model.Task;

public class CreateTaskBehaviour extends Behaviour<TaskSupervisorState, EntityCreatedMessage> {

    @Override
    protected void perform(EntityCreatedMessage message) {
        Task task = (Task) message.getEntity();
        TaskSupervisorState actorState = getActorState();

        ActorRef newTaskActorRef = createTaskActor();
        send(newTaskActorRef, new TaskInitMessage(getActorRef(), task));
        actorState.registerTaskActor(task, newTaskActorRef);

        saveActorState(actorState);
    }

    private ActorRef createTaskActor() {
        return getActorState().getActorContext().actorOf(Props.create(TaskActor.class));
    }

    @Override
    protected Class<EntityCreatedMessage> processMessage() {
        return EntityCreatedMessage.class;
    }
}
