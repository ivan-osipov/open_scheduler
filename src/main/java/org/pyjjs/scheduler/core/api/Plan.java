package org.pyjjs.scheduler.core.api;

import org.pyjjs.scheduler.core.model.primary.DateRange;
import org.pyjjs.scheduler.core.model.primary.TaskResult;

import java.util.Set;

public interface Plan {

    DateRange getRange();

    Set<TaskResult> getPlan();
}
