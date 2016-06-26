OpenScheduler
====
Hi! If you are looking at the moment scheduler with
very simple API, then you found him.

This scheduler based on multi-agent technologies. The implementation based on Akka.

This scheduler define two simple entity types:
* Resource
* Task

Firstly, do you need to create Scheduler. It is simple:
``` java
Scheduler scheduler = SchedulerFactory.get().createScheduler();
```
Secondly, do you need to feel data source; You should be create Resources and Tasks, as in example:
``` java
Resource resource = new Resource();
Task task = new Task();
Task task2 = new Task();
ObservableDataSource dataSource = scheduler.getDataSource();
dataSource.add(resource);
dataSource.add(task);
dataSource.add(task2);
```