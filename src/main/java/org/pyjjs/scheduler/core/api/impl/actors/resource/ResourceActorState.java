package org.pyjjs.scheduler.core.api.impl.actors.resource;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState;
import org.pyjjs.scheduler.core.api.impl.actors.common.SourceBasedActorState;
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.TimeLine;
import org.pyjjs.scheduler.core.model.Resource;

import java.util.HashSet;
import java.util.Set;

public class ResourceActorState extends SourceBasedActorState<Resource> {

    enum Status { CREATED, RECEIVE_REQUEST, DONE;}

    private Double placingPrice = 0d;

    private Set<TimeLine> timeLines = new HashSet<>();

    protected ResourceActorState(ActorContext actorContext) {
        super(actorContext);
    }

    public Double getPlacingPrice() {
        return placingPrice;
    }

    public void setPlacingPrice(Double placingPrice) {
        this.placingPrice = placingPrice;
    }

    public void setCapacity(int capacity) {
        timeLines.clear();
        for (int i = 0; i < capacity; i++) {
            timeLines.add(new TimeLine());
        }
    }

    public int getCapacity() {
        return timeLines.size();
    }

    @Override
    public ActorState copySelf() {
        return this;
    }


}
