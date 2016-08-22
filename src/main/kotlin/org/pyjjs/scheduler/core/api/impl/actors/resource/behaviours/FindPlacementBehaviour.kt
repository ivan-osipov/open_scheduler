package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.IFindAnyPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasNotPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceNotMatchMessage
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActorState
import org.pyjjs.scheduler.core.common.locale.LocaleMessageKeys
import org.pyjjs.scheduler.core.model.criteria.match
import org.pyjjs.scheduler.core.placement.Placement

class FindPlacementBehaviour : Behaviour<ResourceActorState, IFindAnyPlacementMessage>() {

    override fun perform(message: IFindAnyPlacementMessage) {
        val resourceActorState = actorState
        val resource = resourceActorState.source
        if(resource match message.resourceCriteria.strictCriterion) {
            val placement = resourceActorState.placementFinder
                    .findAnyPlacement(message.taskDescriptor, resourceActorState.timeSheet)
            BEHAVIOUR_LOG.info("Resource $actorLocalName found placement for ${message.taskDescriptor}: $placement")
            BEHAVIOUR_LOG.info("Remained free times for resource $actorLocalName: ${resourceActorState.timeSheet.freeTimes}")
            val answer = when (placement.type) {
                Placement.Type.IMPOSSIBLY -> ResourceHasNotPlacementMessage(actorRef)
                else -> ResourceHasPlacementMessage(actorRef, placement)
            }
            answer(message, answer)
        } else {
            logMessage(LocaleMessageKeys.RESOURCE_NOT_MATCH_FOR_CRITERIA, actorLocalName, message.sender?.path()?.name() ?: "UNNAMED")
            answer(message, ResourceNotMatchMessage(actorRef))
        }
    }

    override fun processMessage(): Class<IFindAnyPlacementMessage> {
        return IFindAnyPlacementMessage::class.java
    }
}
