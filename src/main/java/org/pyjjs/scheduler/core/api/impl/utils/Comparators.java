package org.pyjjs.scheduler.core.api.impl.utils;

import org.pyjjs.scheduler.core.api.impl.interfaces.HasTimestamp;
import org.pyjjs.scheduler.core.model.ResourceUsage;

import java.util.Comparator;
import java.util.Objects;

public class Comparators {

    public static final Comparator<HasTimestamp> TIMESTAMP_COMPARATOR = (o1, o2) -> Long.compare(o1.getTimestamp(), o2.getTimestamp());

    public static final Comparator<ResourceUsage> RESOURCE_USAGE_COMPARATOR = (ru1, ru2) -> {
        if (Objects.equals(ru1.getId(), ru1.getId())) return 0;
        return ru1.getDateRange().getStart().compareTo(ru2.getDateRange().getStart());
    };

}
