package org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.behaviours

import akka.actor.ActorRef
import akka.actor.Props
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceInitMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActor
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.ResourceSupervisorState
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.messages.ResourceAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.EntityCreatedMessage
import org.pyjjs.scheduler.core.model.Resource

class CreateResourceBehaviour : Behaviour<ResourceSupervisorState, EntityCreatedMessage>() {

    override fun perform(message: EntityCreatedMessage) {
        val resource = message.entity as Resource
        val actorState = actorState
        if(!isValid(resource)) {
            actorState.invalidResources.add(resource)
            saveActorState(actorState)
            return
        }

        val newResourceActorRef = createResourceActor()
        send(newResourceActorRef, ResourceInitMessage(actorRef, resource))
        actorState.registerTaskActor(resource, newResourceActorRef)

        saveActorState(actorState)

        notifyTasksAboutNewResource(newResourceActorRef, resource)
    }

    private fun isValid(resource: Resource): Boolean {
        try {
            checkNotNull(resource.timeSheet, {"Resource should have availability table"})
            checkNotNull(resource.timeSheet.resourceAvailabilityTable, {"Resource should have availability table"})
            check(!resource.timeSheet.resourceAvailabilityTable.isEmpty(), {"Resource should have records in availability table"})
            return true
        } catch (e: Exception) {
            BEHAVIOUR_LOG.warn("Resource with id: ${resource.id} is invalid. Reason: ${e.message}")
            return false
        }
    }

    private fun notifyTasksAboutNewResource(resourceActorRef: ActorRef, resource: Resource) {
        sendToTaskSupervisor(ResourceAppearedMessage(resourceActorRef, resource, actorRef))
    }

    private fun createResourceActor(): ActorRef {
        return actorState.actorContext.actorOf(Props.create(ResourceActor::class.java))
    }

    override fun processMessage(): Class<EntityCreatedMessage> {
        return EntityCreatedMessage::class.java
    }
}
