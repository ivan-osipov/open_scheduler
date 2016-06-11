package org.pyjjs.scheduler.test;

import org.pyjjs.scheduler.core.api.Scheduler;
import org.pyjjs.scheduler.core.api.SchedulerFactory;
import org.pyjjs.scheduler.core.model.primary.Resource;
import org.pyjjs.scheduler.core.api.microeconomic.MicroeconomicsSchedulerFactory;
import org.pyjjs.scheduler.core.data.ObservableDataSource;
import org.pyjjs.scheduler.core.model.primary.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

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

        Resource resource = new Resource();
        Task task = new Task();
        Task task2 = new Task();
        ObservableDataSource dataSource = scheduler.getDataSource();
        dataSource.add(resource);
        dataSource.add(task);
        dataSource.add(task2);

        scheduler.asyncRun(plan -> System.out.println(plan.toString()));

        try {
            Thread.sleep(2000L);
            Task task3 = new Task();
            dataSource.add(task3);
            Thread.sleep(2000L);
            dataSource.add(task3);
            Thread.sleep(2000L);
            dataSource.remove(task3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
