package org.pyjjs.scheduler.core.actors.resource.messages;

import akka.actor.ActorRef;
import org.pyjjs.scheduler.core.actors.common.Message;

public class ResourcePriceInfoMessage extends Message {

    private Double placingPrice = 0d;

    public ResourcePriceInfoMessage(ActorRef sender) {
        super(sender);
    }

    public Double getPlacingPrice() {
        return placingPrice;
    }

    public void setPlacingPrice(Double placingPrice) {
        this.placingPrice = placingPrice;
    }
}
