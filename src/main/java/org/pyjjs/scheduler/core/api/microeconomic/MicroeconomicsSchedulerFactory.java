package org.pyjjs.scheduler.core.api.microeconomic;

import org.pyjjs.scheduler.core.api.Scheduler;
import org.pyjjs.scheduler.core.api.SchedulerFactory;
import org.pyjjs.scheduler.core.data.ObservableDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MicroeconomicsSchedulerFactory implements SchedulerFactory{

    private ObservableDataSource dataSource;

    @Override
    public Scheduler createScheduler() {
        return new MicroeconomicsBasedMultiAgentScheduler();
    }

    @Override
    @Autowired(required = false)
    public Scheduler createScheduler(ObservableDataSource dataSource) {
        return new MicroeconomicsBasedMultiAgentScheduler(dataSource);
    }
}
