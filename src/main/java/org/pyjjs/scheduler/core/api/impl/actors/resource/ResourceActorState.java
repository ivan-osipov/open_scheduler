package org.pyjjs.scheduler.core.api.impl.actors.resource;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.api.impl.actors.common.ActorState;
import org.pyjjs.scheduler.core.api.impl.actors.common.SourceBasedActorState;
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.JitPlacementFinder;
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.PlacementFinder;
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.TimeLine;
import org.pyjjs.scheduler.core.api.impl.actors.resource.placing.TimeSheet;
import org.pyjjs.scheduler.core.model.Resource;

import java.util.HashSet;
import java.util.Set;

public class ResourceActorState extends SourceBasedActorState<Resource> {

    enum Status { CREATED, RECEIVE_REQUEST, DONE;}

    private Double placingPrice = 0d;

    private TimeSheet timeSheet = new TimeSheet();

    private PlacementFinder placementFinder = new JitPlacementFinder();

    protected ResourceActorState(ActorContext actorContext) {
        super(actorContext);
    }

    public Double getPlacingPrice() {
        return placingPrice;
    }

    public void setPlacingPrice(Double placingPrice) {
        this.placingPrice = placingPrice;
    }

    public TimeSheet getTimeSheet() {
        return timeSheet;
    }

    public void setTimeSheet(TimeSheet timeSheet) {
        this.timeSheet = timeSheet;
    }

    @Override
    public ActorState copySelf() {
        return this;
    }


}
