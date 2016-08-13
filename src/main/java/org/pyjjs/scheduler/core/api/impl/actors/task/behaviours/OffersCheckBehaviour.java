package org.pyjjs.scheduler.core.api.impl.actors.task.behaviours;

import org.pyjjs.scheduler.core.api.impl.actors.common.behaviours.Behaviour;
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.api.impl.actors.task.TaskActorState;
import org.pyjjs.scheduler.core.api.impl.actors.task.messages.CheckOffersMessage;
import org.pyjjs.scheduler.core.common.locale.LocaleMessageKeys;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class OffersCheckBehaviour extends Behaviour<TaskActorState,CheckOffersMessage> {

    @Override
    protected void perform(CheckOffersMessage message) {
        printMessage(LocaleMessageKeys.TASK_PROCESS_OFFERS, getActorLocalName());
        Set<OfferMessage> resourcePrices = getActorState().getOffers();
        Optional<OfferMessage> optionalOffer = resourcePrices
                .stream()
                .min((offer1, offer2) -> offer1.getPlacingPrice().compareTo(offer2.getPlacingPrice()));
        if(optionalOffer.isPresent()) {
            OfferMessage offer = optionalOffer.get();
            printMessage(LocaleMessageKeys.TASK_FOUND_BEST_OFFER, getActorLocalName(), offer.getPlacingPrice(), getActorLocalName(offer.getSender()));
            //TODO react
        } else {
            printMessage(LocaleMessageKeys.TASK_OFFER_NOT_FOUND, getActorLocalName());
        }
    }

    @Override
    protected Class<CheckOffersMessage> processMessage() {
        return CheckOffersMessage.class;
    }
}
