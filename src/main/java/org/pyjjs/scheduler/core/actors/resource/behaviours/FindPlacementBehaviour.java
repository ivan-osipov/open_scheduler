package org.pyjjs.scheduler.core.actors.resource.behaviours;

import org.pyjjs.scheduler.core.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.ResourceActorState;
import org.pyjjs.scheduler.core.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.actors.task.messages.IFindResourceMessage;

public class FindPlacementBehaviour extends Behaviour<ResourceActorState, IFindResourceMessage> {

    @Override
    protected void perform(IFindResourceMessage message) {
        ResourceActorState resourceActorState = getActorState();
        OfferMessage answer = new OfferMessage(getActorRef());
        answer.setPlacingPrice(resourceActorState.getPlacingPrice());
        answer(message, answer);
    }

    @Override
    protected Class<IFindResourceMessage> processMessage() {
        return IFindResourceMessage.class;
    }
}
