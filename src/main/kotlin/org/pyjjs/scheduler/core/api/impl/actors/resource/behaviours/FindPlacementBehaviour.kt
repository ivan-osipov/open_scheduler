package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.messages.*
import org.pyjjs.scheduler.core.common.locale.LocaleMessageKeys
import org.pyjjs.scheduler.core.model.criteria.match
import org.pyjjs.scheduler.core.placement.Placement
import kotlin.reflect.KClass

class FindPlacementBehaviour : ResourceBehaviour<IFindAnyPlacementMessage>() {

    override fun perform(message: IFindAnyPlacementMessage) {
        val resource = actorState.source
        if(resource match message.taskDescriptor) {
            val copyOfTimeSheet = actorState.timeSheet.getCopy()
            val placement: Placement = actorState.placementFinder
                    .findAnyPlacement(message.taskDescriptor, copyOfTimeSheet)
            val answerMessage = when (placement.type) {
                Placement.Type.IMPOSSIBLY -> ResourceHasNotPlacementMessage(actorRef, RejectionReason.HAS_NOT_FREE_TIME)

                Placement.Type.FULL -> {
                    if(placement.offer == null) throw IllegalStateException("Placement has not offer")
                    BEHAVIOUR_LOG.info("Resource $actorLocalName found full placement for ${message.taskDescriptor}: $placement")
                    actorState.offersById[placement.offer.id] = placement.offer
                    actorState.timeSheetsByOfferId[placement.offer.id] = copyOfTimeSheet
                    ResourceHasFullPlacementMessage(actorRef, actorState.source, placement.offer)
                }

                Placement.Type.PARTIAL -> {
                    if(placement.offer == null) throw IllegalStateException("Placement has not offer")
                    BEHAVIOUR_LOG.info("Resource $actorLocalName found partial placement for ${message.taskDescriptor}: $placement")
                    actorState.offersById[placement.offer.id] = placement.offer
                    actorState.timeSheetsByOfferId[placement.offer.id] = copyOfTimeSheet
                    ResourceHasPartiallyPlacementMessage(actorRef, actorState.source, placement.offer)
                }
            }
            saveActorState(actorState)
            answer(message, answerMessage)
            BEHAVIOUR_LOG.info("Remained free times for resource $actorLocalName: ${copyOfTimeSheet.freeTimes}")
        } else {
            logMessage(LocaleMessageKeys.RESOURCE_NOT_MATCH_FOR_CRITERIA, actorLocalName, message.sender?.path()?.name() ?: "UNNAMED")
            answer(message, ResourceHasNotPlacementMessage(actorRef, RejectionReason.NOT_MATCH))
        }
    }

    override fun processMessage(): KClass<IFindAnyPlacementMessage> {
        return IFindAnyPlacementMessage::class
    }
}
