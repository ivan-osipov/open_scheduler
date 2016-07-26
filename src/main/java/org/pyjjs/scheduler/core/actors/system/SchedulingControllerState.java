package org.pyjjs.scheduler.core.actors.system;

import akka.actor.ActorContext;
import org.pyjjs.scheduler.core.actors.common.ActorState;
import org.pyjjs.scheduler.core.api.PlanChange;
import org.pyjjs.scheduler.core.api.SchedulingListener;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SchedulingControllerState extends ActorState {

    private Set<SchedulingListener> schedulingListeners;

    private SortedSet<PlanChange> planChanges = new TreeSet<>((c1, c2) -> Long.compare(c1.getTimestamp(),c2.getTimestamp()));

    public SchedulingControllerState(ActorContext actorContext) {
        super(actorContext);
    }

    @Override
    protected ActorState copySelf() {
        return this;
    }

    public Set<SchedulingListener> getSchedulingListeners() {
        return schedulingListeners;
    }

    public void setSchedulingListeners(Set<SchedulingListener> schedulingListeners) {
        this.schedulingListeners = schedulingListeners;
    }

    public SortedSet<PlanChange> getPlanChanges() {
        return planChanges;
    }

    public void setPlanChanges(SortedSet<PlanChange> planChanges) {
        this.planChanges = planChanges;
    }
}
