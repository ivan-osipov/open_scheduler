package org.pyjjs.scheduler.test

import org.pyjjs.scheduler.core.api.SchedulerFactory
import org.pyjjs.scheduler.core.api.impl.actors.common.messages.TaskDescriptor
import org.pyjjs.scheduler.core.model.Resource
import org.pyjjs.scheduler.core.model.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.stereotype.Component
import java.util.*

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
            val resource0 = Resource()
            resource0.id = UUID.randomUUID()
            dataSource.add(resource0)

            val task = Task(TaskDescriptor(
                    laborContent = 10.0,
                    minCapacity = 1.0,
                    maxCapacity = 3.0,
                    minDuration = 1,
                    maxDuration = 5))
            task.id = UUID.randomUUID()
            val task2 = Task(TaskDescriptor(
                    laborContent = 10.0,
                    minCapacity = 1.0,
                    maxCapacity = 3.0,
                    minDuration = 1,
                    maxDuration = 5))
            task2.id = UUID.randomUUID()
            val task3 = Task(TaskDescriptor(
                    laborContent = 10.0,
                    minCapacity = 1.0,
                    maxCapacity = 3.0,
                    minDuration = 1,
                    maxDuration = 5))
            task3.id = UUID.randomUUID()
            dataSource.add(task)
            Thread.sleep(2000L)
            dataSource.add(task2)
            Thread.sleep(2000L)
            dataSource.add(task3)

            Thread.sleep(2000L)
            val resource = Resource()
            resource.id = UUID.randomUUID()
            dataSource.add(resource)
            val resource1 = Resource()
            resource.id = UUID.randomUUID()
            dataSource.add(resource1)

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}