package org.pyjjs.scheduler.core.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Task extends IdentifiableObject {

    private long duration;
    private Date minStartDate;
    private Date deadline;
    private TaskResult taskResult;

    private Set<Task> successors = new HashSet<>();
    private Set<Task> predecessors = new HashSet<>();

    private Set<Task> parents = new HashSet<>();
    private Set<Task> childs = new HashSet<>();

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getMinStartDate() {
        return minStartDate;
    }

    public void setMinStartDate(Date minStartDate) {
        this.minStartDate = minStartDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public TaskResult getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(TaskResult taskResult) {
        this.taskResult = taskResult;
    }

    public Set<Task> getSuccessors() {
        return successors;
    }

    public void setSuccessors(Set<Task> successors) {
        this.successors = successors;
    }

    public Set<Task> getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(Set<Task> predecessors) {
        this.predecessors = predecessors;
    }

    public Set<Task> getParents() {
        return parents;
    }

    public void setParents(Set<Task> parents) {
        this.parents = parents;
    }

    public Set<Task> getChilds() {
        return childs;
    }

    public void setChilds(Set<Task> childs) {
        this.childs = childs;
    }
}
