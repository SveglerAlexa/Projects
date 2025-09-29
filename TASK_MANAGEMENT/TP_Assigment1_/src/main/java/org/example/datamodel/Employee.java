package org.example.datamodel;

import org.example.bussinesslogic.TasksManagement;
import org.example.dataaccess.LoadSave;

import java.io.Serializable;

public class Employee implements Comparable,Serializable {
    private static final long serialVersionUID = 1L;
    private int idEmployee;
    private String name;
    private int workDuration;


    public Employee(int idEmployee, String name) {
        this.idEmployee = idEmployee;
        this.name = name;
        this.workDuration = 0;

    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void WorkDuration(TasksManagement tasksManagement) {
        this.workDuration=tasksManagement.calculateEmployeeWorkDuration(this.idEmployee);

    }

    public int getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(int workDuration) {
        this.workDuration = workDuration;
    }

    @Override
    public int compareTo(Object o) {
        return this.getWorkDuration()-((Employee)o).getWorkDuration();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", name='" + name + '\'' +
                ", workDuration=" + workDuration +
                '}';
    }

    public void afisare()
    {
        System.out.println(this.toString());
    }
}
