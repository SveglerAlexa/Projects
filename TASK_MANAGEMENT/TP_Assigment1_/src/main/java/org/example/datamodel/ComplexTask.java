package org.example.datamodel;

import org.example.dataaccess.LoadSave;

import java.io.Serializable;
import java.util.ArrayList;

public non-sealed class ComplexTask extends Task implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<Task> taskComplex;
    private int duration;

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
        this.taskComplex = new ArrayList<>();
        this.duration =0;
    }

    public void addTask(Task task) {
       taskComplex.add(task);
    }

    public void deleteTask(Task task) {
        taskComplex.remove(task);
    }


    public int estimateDuration() {
        int duration = 0;
        for (Task task : taskComplex) {
            if(task instanceof ComplexTask) {
                duration += ((ComplexTask)task).estimateDuration();
            }
            else if(task instanceof SimpleTask) {
                duration += task.estimateDuration();
            }
        }
        this.duration = duration;
        return duration;
    }

    public ArrayList<Task> getTaskComplex() {
        return taskComplex;
    }

    public void setTaskComplex(ArrayList<Task> taskComplex) {
        this.taskComplex = taskComplex;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "ComplexTask{" +
                " idTask=" + getIdTask() +
                ", status="+ getStatusTask() +
                ", taskComplex=" + taskComplex +
                ", Estimate duration=" + duration +
                '}';
    }
}
