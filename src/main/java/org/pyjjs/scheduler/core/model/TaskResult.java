package org.pyjjs.scheduler.core.model;

import com.google.common.collect.Sets;

import java.util.Set;

public class TaskResult extends IdentifiableObject{

    private Task task;

    private Set<ResourceUsage> resourceUsages = Sets.newHashSet();

    public TaskResult(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Set<ResourceUsage> getResourceUsages() {
        return resourceUsages;
    }

    public void setResourceUsages(Set<ResourceUsage> resourceUsages) {
        this.resourceUsages = resourceUsages;
    }
}
