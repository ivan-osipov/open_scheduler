package org.pyjjs.scheduler.core.api.impl.actors.resource.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.resource.ResourceActorState;
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.api.impl.actors.task.messages.IFindResourceMessage;

public class FindPlacementBehaviour extends Behaviour<ResourceActorState, IFindResourceMessage> {

    @Override
    protected void perform(IFindResourceMessage message) {
        ResourceActorState resourceActorState = getActorState();
        OfferMessage answer = new OfferMessage(getActorRef());
        answer.setPlacingPrice(resourceActorState.getPlacingPrice());
        answer.setResource(getActorState().getSource());
        answer(message, answer);
    }

    @Override
    protected Class<IFindResourceMessage> processMessage() {
        return IFindResourceMessage.class;
    }
}
