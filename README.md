OpenScheduler
====
Hi! If you are looking for a scheduler with
very simple API, then you found him.

This scheduler is based on multi-agent technologies. The implementation based on Akka.

This scheduler defines two simple entity types:
* Resource
* Task

Firstly, you need to create Scheduler. It is simple:
``` java
Scheduler scheduler = SchedulerFactory.get().createScheduler();
```
Secondly, you need to feel a data source; You should create Resources and Tasks, look the example below:
``` java
Resource resource = new Resource();
Task task = new Task();
Task task2 = new Task();
ObservableDataSource dataSource = scheduler.getDataSource();
dataSource.add(resource);
dataSource.add(task);
dataSource.add(task2);
```
