package org.pyjjs.scheduler.core.api.impl.actors.system.messages;

import com.google.common.collect.Sets;
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.Message;
import org.pyjjs.scheduler.core.api.impl.changes.PlanChange;
import org.pyjjs.scheduler.core.api.impl.utils.Comparators;

import java.util.Set;
import java.util.SortedSet;

public class PlanUpdatedMessage extends Message {

    private SortedSet<PlanChange> planChanges = Sets.newTreeSet( Comparators.TIMESTAMP_COMPARATOR );

    public SortedSet<PlanChange> getPlanChanges() {
        return planChanges;
    }
}
