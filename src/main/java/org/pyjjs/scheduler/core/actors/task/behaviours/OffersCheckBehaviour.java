package org.pyjjs.scheduler.core.actors.task.behaviours;

import org.pyjjs.scheduler.core.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.actors.task.messages.CheckOffersMessage;

import java.util.Set;

public class OffersCheckBehaviour extends Behaviour<TaskActorState,CheckOffersMessage> {

    @Override
    protected void perform(CheckOffersMessage message) {
        System.out.println("Task " + getActorRef() + " check resource prices");
        Set<OfferMessage> resourcePrices = getActorState().getOffers();
        for (OfferMessage resourcePrice : resourcePrices) {
            System.out.println("Resource" + resourcePrice.getSender() + " предложил " + resourcePrice.getPlacingPrice());
        }
    }

    @Override
    protected Class<CheckOffersMessage> processMessage() {
        return CheckOffersMessage.class;
    }
}
