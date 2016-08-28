package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.ResourceHasFullPlacementMessage
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState
import java.util.*

abstract class TaskBehaviour<M : Message> : Behaviour<TaskActorState, M>() {

    fun getOfferById(offerId : UUID): ResourceHasFullPlacementMessage?{
        return actorState.offersById[offerId]
    }

}
