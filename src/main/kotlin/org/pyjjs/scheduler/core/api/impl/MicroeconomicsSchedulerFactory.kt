package org.pyjjs.scheduler.core.api.impl

import org.pyjjs.scheduler.core.api.Scheduler
import org.pyjjs.scheduler.core.api.SchedulerFactory
import org.pyjjs.scheduler.core.data.ObservableDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
open class MicroeconomicsSchedulerFactory : SchedulerFactory {

    override fun createScheduler(): Scheduler {
        return SchedulerImpl()
    }

    override fun createScheduler(@Autowired dataSource: ObservableDataSource): Scheduler {
        return SchedulerImpl(dataSource)
    }
}
