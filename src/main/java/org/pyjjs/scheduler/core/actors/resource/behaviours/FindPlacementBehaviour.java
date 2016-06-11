package org.pyjjs.scheduler.core.actors.resource.behaviours;

import org.pyjjs.scheduler.core.actors.common.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.ResourceActorState;
import org.pyjjs.scheduler.core.actors.resource.messages.FoundResourceMessage;
import org.pyjjs.scheduler.core.actors.task.messages.IFindSomeResourceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindPlacementBehaviour extends Behaviour<ResourceActorState, IFindSomeResourceMessage> {

    private static final FindPlacementBehaviour INSTANCE = new FindPlacementBehaviour();

    private FindPlacementBehaviour(){}

    public static FindPlacementBehaviour get() {
        return INSTANCE;
    }

    @Override
    protected void perform(IFindSomeResourceMessage message) {
        answer(message, new FoundResourceMessage(getActorRef()));
    }

    @Override
    protected Class<IFindSomeResourceMessage> processMessage() {
        return IFindSomeResourceMessage.class;
    }
}
