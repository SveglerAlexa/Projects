package org.example.datamodel;

import java.io.Serializable;

public non-sealed class SimpleTask extends Task implements Serializable {
    private static final long serialVersionUID = 1L;
    private int startHour;
    private int endHour;
    private int duration;

    public SimpleTask(int startHour, int endHour, int idTask, String status) {
        super(idTask, status);
        this.startHour = startHour;
        this.endHour = endHour;
        this.duration = endHour - startHour;
    }

    public  int estimateDuration()
    {
        this.duration = endHour - startHour;
        return endHour - startHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "idTask=" + getIdTask() +
                ", status=" + getStatusTask()+
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", Estimate duration=" + duration +
                '}';
    }
}
