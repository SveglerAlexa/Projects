package org.example.bussinesslogic;

import org.example.dataaccess.LoadSave;
import org.example.datamodel.ComplexTask;
import org.example.datamodel.Employee;
import org.example.datamodel.SimpleTask;
import org.example.datamodel.Task;

import java.io.Serializable;
import java.util.Map;
import java.util.*;

public class TasksManagement implements Serializable {

    private ArrayList<Task> allTasks=new ArrayList<>();
    private ArrayList<Employee> allEmployees=new ArrayList<>();
    private Map<Employee,ArrayList<Task>> map = new HashMap<>();


    public TasksManagement() {

        this.allTasks=LoadSave.loadAllTasks();
        this.map=LoadSave.loadMap();
        this.allEmployees=LoadSave.loadEmployees();
    }

    public Task returnTask(int idTask) {
        for (Task task : allTasks) {
            if (task.getIdTask() == idTask) {
                return task;
            }
        }
        return null;

    }

    public void addTask(Task task) {
        allTasks.add(task);
        LoadSave.saveAllTasks(allTasks);
        LoadSave.saveMap(map);
    }
    public void addEmployee(Employee employee) {
        map.put(employee,new ArrayList<>());
        allEmployees.add(employee);
        LoadSave.saveMap(map);
        LoadSave.saveEmployees(allEmployees);

    }


    public void verificareAdaugare(int idCmpTask,int idTask)
    {
        for(Map.Entry<Employee,ArrayList<Task>> entry:map.entrySet())
        {
            Employee employee = entry.getKey();
            ArrayList<Task> tasks = entry.getValue();

            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getIdTask() == idCmpTask) {
                    for(Task t:allTasks)
                        if(t.getIdTask() == idTask)
                        {   ((ComplexTask)task).addTask(t);
                            employee.WorkDuration(this);
                        }

                }
            }
        }

        LoadSave.saveMap(map);

        for(Task task : allTasks) {
            if(task.getIdTask() == idCmpTask) {
                for(Task subTask: allTasks) {
                    if(subTask.getIdTask() == idTask) {
                        ((ComplexTask) task).addTask(subTask);
                        ((ComplexTask) task).setDuration(((ComplexTask) task).estimateDuration());

                    }
                }
            }

        }
        LoadSave.saveAllTasks(allTasks);
    }

    public void verificareStergere(int idCmpTask,int idTask)
    {

        for(Map.Entry<Employee,ArrayList<Task>> entry : map.entrySet())
        {
            Employee employee = entry.getKey();
            ArrayList<Task> tasks = entry.getValue();

            Iterator<Task> iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getIdTask() == idCmpTask) {
                    for(Task t:allTasks)
                        if(t.getIdTask() == idTask)
                        {   ((ComplexTask)task).deleteTask(t);
                            employee.WorkDuration(this);
                        }
                }
            }

        }
        LoadSave.saveMap(map);


        for(Task task : allTasks) {
            if(task.getIdTask() == idCmpTask) {
                for(Task subTask: allTasks) {
                    if(subTask.getIdTask() == idTask) {
                        ((ComplexTask) task).deleteTask(subTask);
                        ((ComplexTask) task).setDuration(((ComplexTask) task).estimateDuration());
                    }
                }
            }

        }
        LoadSave.saveAllTasks(allTasks);
    }

    public void assignTaskToEmployee(int idEmployee,int id_task)
    {
        for(Map.Entry<Employee,ArrayList<Task>> entry: map.entrySet())
        {
            Employee employee = entry.getKey();
            if(employee.getIdEmployee() == idEmployee) {
                for(Task t:allTasks)
                    if(t.getIdTask() == id_task) {
                        map.get(employee).add(t);
                        employee.WorkDuration(this);

                    }
            }

            LoadSave.saveAllTasks(allTasks);
            LoadSave.saveMap(map);
        }

    }

    public int calculateEmployeeWorkDuration(int idEmployee)
    {
        int duration = 0;

        for(Map.Entry<Employee,ArrayList<Task>> entry: map.entrySet())
        {
            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();
            if(employee.getIdEmployee() == idEmployee)
            {
                for(Task t: tasks)
                {
                    if(t instanceof SimpleTask)
                        duration=duration+t.estimateDuration();
                    else if(t instanceof ComplexTask)
                        duration=duration+((ComplexTask)t).estimateDuration();

                }
            }


        }
        LoadSave.saveMap(map);
        return duration;
    }

    public void modifyTaskStatus(int idEmployee,int idTask)
    {
        for(Map.Entry<Employee,ArrayList<Task>> entry: map.entrySet())
        {
            Employee employee = entry.getKey();
            ArrayList<Task> tasks = entry.getValue();
            int nrUncompletedTasks = 0;
            if(employee.getIdEmployee() == idEmployee)
            {
                for(Task t: tasks)
                {

                    if(t instanceof ComplexTask)
                    {
                        ArrayList<Task> j=((ComplexTask) t).getTaskComplex();
                        for(Task t2: j)
                        {
                            if(t2.getIdTask() == idTask)
                            {
                                if(t2.getStatusTask().equals("Completed"))
                                    t2.setStatusTask("Uncompleted");
                                else if(t2.getStatusTask().equals("Uncompleted"))
                                    t2.setStatusTask("Completed");
                            }
                            if(t2.getStatusTask().equals("Uncompleted"))
                                nrUncompletedTasks++;
                        }
                        if(nrUncompletedTasks == 0)
                            t.setStatusTask("Completed");
                        else t.setStatusTask("Uncompleted");
                    }
                    else if(t instanceof SimpleTask)
                    if(t.getIdTask() == idTask)
                        if(t.getStatusTask().equals("Completed"))
                            t.setStatusTask("Uncompleted");
                        else if(t.getStatusTask().equals("Uncompleted"))
                            t.setStatusTask("Completed");


                }
                LoadSave.saveMap(map);
            }


        }
    }



    public Map<Employee, ArrayList<Task>> getMap() {
        return map;
    }


    public ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    public ArrayList<Employee> getAllEmployees() {
        return allEmployees;
    }

    public void afisareTaskuri()///de sters
    {
        for(Task t: allTasks)
        {
            System.out.println(t);

        }
    }
}


