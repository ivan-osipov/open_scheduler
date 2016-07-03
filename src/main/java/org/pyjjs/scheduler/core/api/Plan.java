package org.pyjjs.scheduler.core.api;

import org.pyjjs.scheduler.core.model.DateRange;
import org.pyjjs.scheduler.core.model.TaskResult;

import java.util.Set;

public interface Plan {

    DateRange getRange();

    Set<TaskResult> getPlan();
}
