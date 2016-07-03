package org.pyjjs.scheduler.test;

import org.pyjjs.scheduler.core.api.Scheduler;
import org.pyjjs.scheduler.core.api.SchedulerFactory;
import org.pyjjs.scheduler.core.model.Resource;
import org.pyjjs.scheduler.core.data.ObservableDataSource;
import org.pyjjs.scheduler.core.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Application {

    @Autowired
    private SchedulerFactory schedulerFactory;

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("main-context.xml");
        Application application = context.getBean(Application.class);
        application.run(args);

    }

    public void run(String[] args) {
        Scheduler scheduler = schedulerFactory.createScheduler();


        scheduler.asyncRun(plan -> System.out.println(plan.toString()));

        ObservableDataSource dataSource = scheduler.getDataSource();

        try {
            Thread.sleep(2000L);
            Resource resource = new Resource();
            resource.setId(UUID.randomUUID());
            dataSource.add(resource);
            Resource resource1 = new Resource();
            resource.setId(UUID.randomUUID());
            dataSource.add(resource1);

            Thread.sleep(2000L);
            Task task = new Task();
            task.setId(UUID.randomUUID());
            Task task2 = new Task();
            task2.setId(UUID.randomUUID());
            Task task3 = new Task();
            task3.setId(UUID.randomUUID());
            dataSource.add(task);
            Thread.sleep(2000L);
            dataSource.add(task2);
            Thread.sleep(2000L);
            dataSource.add(task3);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
