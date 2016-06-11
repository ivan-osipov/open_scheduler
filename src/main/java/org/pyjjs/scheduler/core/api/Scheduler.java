package org.pyjjs.scheduler.core.api;

import org.pyjjs.scheduler.core.data.ObservableDataSource;

public interface Scheduler {

    void asyncRun(PlanCompleteListener... listeners);

    Plan syncRun();

    boolean isRunning();

    void reset();

    ObservableDataSource getDataSource();

    void addPlanCompleteListener(PlanCompleteListener listener);

    void removePlanCompleteListener(PlanCompleteListener listener);
}
