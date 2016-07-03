package org.pyjjs.scheduler.core.actors.resource.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.Message;

public class OfferMessage extends Message {

    private Double placingPrice = 0d;

    public OfferMessage(ActorRef sender) {
        super(sender);
    }

    public Double getPlacingPrice() {
        return placingPrice;
    }

    public void setPlacingPrice(Double placingPrice) {
        this.placingPrice = placingPrice;
    }
}
