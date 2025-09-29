package org.example.datamodel;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits ComplexTask,SimpleTask {
    private static final long serialVersionUID = 1L;
    private int idTask;
    private String statusTask;
    public abstract int estimateDuration();

    public Task(int idTask, String statusTask) {
        this.idTask = idTask;
        this.statusTask = statusTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", statusTask='" + statusTask + '\'' +
                '}';
    }
}
