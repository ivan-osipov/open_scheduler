package org.pyjjs.scheduler.core.actors.resource.supervisor.behaviours;

import akka.actor.ActorRef;
import akka.actor.Props;
import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.ResourceActor;
import org.pyjjs.scheduler.core.actors.resource.supervisor.ResourceSupervisorState;
import org.pyjjs.scheduler.core.actors.resource.supervisor.messages.ResourceInitMessage;
import org.pyjjs.scheduler.core.actors.system.messages.EntityCreatedMessage;
import org.pyjjs.scheduler.core.model.Resource;

public class CreateResourceBehaviour extends Behaviour<ResourceSupervisorState, EntityCreatedMessage> {

    @Override
    protected void perform(EntityCreatedMessage message) {
        Resource resource = (Resource) message.getEntity();
        ResourceSupervisorState actorState = getActorState();

        ActorRef newResourceActorRef = createResourceActor();
        send(newResourceActorRef, new ResourceInitMessage(getActorRef(), resource));
        actorState.registerTaskActor(resource, newResourceActorRef);

        saveActorState(actorState);
    }

    private ActorRef createResourceActor() {
        return getActorState().getActorContext().actorOf(Props.create(ResourceActor.class));
    }

    @Override
    protected Class<EntityCreatedMessage> processMessage() {
        return EntityCreatedMessage.class;
    }
}
