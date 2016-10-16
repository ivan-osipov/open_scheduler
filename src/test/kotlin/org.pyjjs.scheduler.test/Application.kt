package org.pyjjs.scheduler.test

import org.pyjjs.scheduler.core.api.SchedulerFactory
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor as TD
import org.pyjjs.scheduler.core.api.impl.utils.withId
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.stereotype.Component

fun main(args: Array<String>) {

    val context = ClassPathXmlApplicationContext("main-context.xml")
    val application = context.getBean(Application::class.java)
    application.run()

}

@Component
class Application {

    @Autowired
    lateinit var schedulerFactory: SchedulerFactory

    fun run() {
        val scheduler = schedulerFactory.createScheduler()

        scheduler.run()

        val dataSource = scheduler.dataSource

        try {
            val resource0 = Resource().withId()
            dataSource.add(resource0)

            val task = Task(TD(
                    laborContent = 10.0,
                    minCapacity = 1.0,
                    maxCapacity = 3.0,
                    minDuration = 1,
                    maxDuration = 5)).withId()
            val task2 = Task(TD(
                    laborContent = 10.0,
                    minCapacity = 1.0,
                    maxCapacity = 3.0,
                    minDuration = 1,
                    maxDuration = 5)).withId()
            val task3 = Task(TD(
                    laborContent = 10.0,
                    minCapacity = 1.0,
                    maxCapacity = 3.0,
                    minDuration = 1,
                    maxDuration = 5)).withId()
            dataSource.add(task)
            Thread.sleep(2000L)
            dataSource.add(task2)
            Thread.sleep(2000L)
            dataSource.add(task3)

            Thread.sleep(2000L)
            val resource = Resource().withId()
            dataSource.add(resource)
            val resource1 = Resource().withId()
            dataSource.add(resource1)

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}