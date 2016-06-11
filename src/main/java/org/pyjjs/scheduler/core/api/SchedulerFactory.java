package org.pyjjs.scheduler.core.api;

import org.pyjjs.scheduler.core.data.ObservableDataSource;

public interface SchedulerFactory {

    Scheduler createScheduler();

    Scheduler createScheduler(ObservableDataSource dataSource);
}
