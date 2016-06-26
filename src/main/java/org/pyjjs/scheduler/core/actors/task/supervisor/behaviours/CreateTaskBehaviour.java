package org.pyjjs.scheduler.core.actors.task.supervisor.behaviours;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.common.SystemHelper;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActor;
import org.pyjjs.scheduler.core.actors.task.supervisor.TaskSupervisorState;
import org.pyjjs.scheduler.core.actors.task.supervisor.messages.TaskInitMessage;
import org.pyjjs.scheduler.core.model.primary.Task;

public class CreateTaskBehaviour extends Behaviour<TaskSupervisorState, EntityCreatedMessage> {

    private static final CreateTaskBehaviour INSTANCE = new CreateTaskBehaviour();

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

    public static CreateTaskBehaviour get() {
        return INSTANCE;
    }
}
