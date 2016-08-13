package org.pyjjs.scheduler.core.api.impl.actors.task;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState;
import org.pyjjs.scheduler.core.api.impl.actors.common.SourceBasedActorState;
import org.pyjjs.scheduler.core.api.impl.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.model.Task;

import java.util.HashSet;
import java.util.Set;

public class TaskActorState extends SourceBasedActorState<Task> {

    private Set<OfferMessage> offers = new HashSet<>();
    private boolean checkOffersScheduled = false;
    private Double discontent = null;

    public TaskActorState(ActorContext actorContext) {
        super(actorContext);
    }

    public void addOffer(OfferMessage resourcePrice) {
        offers.add(resourcePrice);
    }

    public Set<OfferMessage> getOffers() {
        return offers;
    }

    public boolean checkOffersIsScheduled() {
        return checkOffersScheduled;
    }

    public void setCheckOffersScheduled(boolean checkOffersScheduled) {
        this.checkOffersScheduled = checkOffersScheduled;
    }

    public Double getDiscontent() {
        return discontent;
    }

    public void setDiscontent(Double discontent) {
        this.discontent = discontent;
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
