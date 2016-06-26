package org.pyjjs.scheduler.core.actors.resource.behaviours;

import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.common.Message;
import org.pyjjs.scheduler.core.actors.resource.ResourceActorState;
import org.pyjjs.scheduler.core.actors.resource.messages.FoundResourceMessage;
import org.pyjjs.scheduler.core.actors.resource.messages.ResourcePriceInfoMessage;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindPlacementBehaviour extends Behaviour<ResourceActorState, IFindSomeResourceMessage> {

    @Override
    protected void perform(IFindSomeResourceMessage message) {
        ResourceActorState resourceActorState = getActorState();
        ResourcePriceInfoMessage answer = new ResourcePriceInfoMessage(getActorRef());
        answer.setPlacingPrice(resourceActorState.getPlacingPrice());
        answer(message, answer);
    }

    @Override
    protected Class<IFindSomeResourceMessage> processMessage() {
        return IFindSomeResourceMessage.class;
    }
}
