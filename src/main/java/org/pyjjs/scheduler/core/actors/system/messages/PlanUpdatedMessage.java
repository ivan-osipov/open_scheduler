package org.pyjjs.scheduler.core.actors.system.messages;

import com.google.common.collect.Sets;
import org.pyjjs.scheduler.core.actors.common.messages.Message;
import org.pyjjs.scheduler.core.api.PlanChange;

import java.util.Set;

public class PlanUpdatedMessage extends Message {

    private Set<PlanChange> planChanges = Sets.newHashSet();

    public Set<PlanChange> getPlanChanges() {
        return planChanges;
    }

    public void setPlanChanges(Set<PlanChange> planChanges) {
        this.planChanges = planChanges;
    }
}
