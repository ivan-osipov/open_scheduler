package org.pyjjs.scheduler.core.api.impl.utils;

import org.pyjjs.scheduler.core.api.impl.interfaces.HasTimestamp;
import org.pyjjs.scheduler.core.model.ResourceUsage;

import java.util.Comparator;
import java.util.Objects;

public class Comparators {

    public static final Comparator<HasTimestamp> TIMESTAMP_COMPARATOR = (o1, o2) -> {
        int compareResult = Long.compare(o1.getTimestamp(), o2.getTimestamp());
        return (compareResult == 0) ? 1 : compareResult;
    };

    public static final Comparator<ResourceUsage> RESOURCE_USAGE_COMPARATOR = (ru1, ru2) -> {
        if (Objects.equals(ru1.getId(), ru2.getId())) return 0;
        int compareResult = ru1.getDateRange().getStart().compareTo(ru2.getDateRange().getStart());
        return compareResult == 0 ? 1 : compareResult;
    };



}
