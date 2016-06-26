package org.pyjjs.scheduler.core.actors.resource;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.SourceBasedActorState;
import org.pyjjs.scheduler.core.model.primary.Resource;

public class ResourceActorState extends SourceBasedActorState<Resource> {

    enum Status { CREATED, RECEIVE_REQUEST, DONE;}

    private Double placingPrice = 0d;

    protected ResourceActorState(ActorContext actorContext) {
        super(actorContext);
    }

    public Double getPlacingPrice() {
        return placingPrice;
    }

    public void setPlacingPrice(Double placingPrice) {
        this.placingPrice = placingPrice;
    }

    @Override
    public ActorState copySelf() {
        return this;
    }


}
