package org.pyjjs.scheduler.core.actors.resource.behaviours;

import org.pyjjs.scheduler.core.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.ResourceActorState;
import org.pyjjs.scheduler.core.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;

public class FindPlacementBehaviour extends Behaviour<ResourceActorState, IFindSomeResourceMessage> {

    @Override
    protected void perform(IFindSomeResourceMessage message) {
        ResourceActorState resourceActorState = getActorState();
        OfferMessage answer = new OfferMessage(getActorRef());
        answer.setPlacingPrice(resourceActorState.getPlacingPrice());
        answer(message, answer);
    }

    @Override
    protected Class<IFindSomeResourceMessage> processMessage() {
        return IFindSomeResourceMessage.class;
    }
}
