package org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.behaviours

import akka.actor.ActorRef
import akka.actor.Props
import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceAppearedMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActor
import org.pyjjs.scheduler.core.api.impl.actors.resource.supervisor.ResourceSupervisorState
import org.pyjjs.scheduler.core.api.impl.actors.system.messages.EntityCreatedMessage
import org.pyjjs.scheduler.core.model.Resource

class CreateResourceBehaviour : Behaviour<ResourceSupervisorState, EntityCreatedMessage>() {

    override fun perform(message: EntityCreatedMessage) {
        val resource = message.entity as Resource
        if(!isValid(resource)) {
            actorState.invalidResources.add(resource)
            saveActorState(actorState)
            return
        }

        val newResourceActorRef = createResourceActor(resource)
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

    private fun createResourceActor(resource: Resource): ActorRef {
        return actorState.actorContext.actorOf(Props.create(ResourceActor::class.java, resource))
    }

    override fun processMessage(): Class<EntityCreatedMessage> {
        return EntityCreatedMessage::class.java
    }
}
