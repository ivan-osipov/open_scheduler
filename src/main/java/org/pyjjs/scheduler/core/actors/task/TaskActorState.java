package org.pyjjs.scheduler.core.actors.task;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.actors.common.SourceBasedActorState;
import org.pyjjs.scheduler.core.actors.resource.messages.OfferMessage;
import org.pyjjs.scheduler.core.model.Task;

import java.util.HashSet;
import java.util.Set;

public class TaskActorState extends SourceBasedActorState<Task> {

    private Set<OfferMessage> offers = new HashSet<>();
    private boolean checkOffersScheduled = false;

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

    @Override
    protected ActorState copySelf() {
        return this;
    }
}
