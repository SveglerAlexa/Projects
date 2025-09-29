package org.example.bussinesslogic;

import org.example.datamodel.Employee;
import org.example.datamodel.Task;

import java.util.*;

public class Utility {

    private ArrayList<Employee> employees=new ArrayList<>();
    private Map<String,Integer> taskStatus=new HashMap<>();
    private Map<String,Map<String,Integer>> nrTaskuri=new HashMap<>();


    public ArrayList<Employee> workDuration40(TasksManagement m)
    {
        int workDuration = 0;
        for(Map.Entry<Employee, ArrayList<Task>> entry : m.getMap().entrySet())
        {
            int ok=0;
            Employee employee = entry.getKey();
            for(Employee e:employees)
            {
                if(e.getIdEmployee()==employee.getIdEmployee())
                    ok=1;
            }

            if(m.calculateEmployeeWorkDuration(employee.getIdEmployee())>40&&ok==0)
                employees.add(employee);

        }
        Collections.sort(employees);


        return employees;
    }

    public void numberOfTasks(TasksManagement m)
    {
        for(Map.Entry<Employee, ArrayList<Task>> entry : m.getMap().entrySet())
        {
            Employee employee = entry.getKey();
            List<Task> tasks = entry.getValue();
            int completedTasks = 0;
            int uncompletedTasks = 0;
            for(Task t:tasks)
            {
                if(t.getStatusTask().equals("Completed"))
                    completedTasks++;
                else if(t.getStatusTask().equals("Uncompleted"))
                    uncompletedTasks++;
            }
            Map<String,Integer> taskStatus=new HashMap<>();
            taskStatus.put("Completed",completedTasks);
            taskStatus.put("Uncompleted",uncompletedTasks);
            nrTaskuri.put(employee.getName(),taskStatus);
        }
    }

    public Map<String,Map<String,Integer>> getMap()
    {
        return nrTaskuri;
    }

    public void display()///de sters
    {
        for (Map.Entry<String, Map<String, Integer>> entry : nrTaskuri.entrySet()) {
            System.out.println("Employee: " + entry.getKey());
            System.out.println("  Completed: " + entry.getValue().get("Completed"));
            System.out.println("  Uncompleted: " + entry.getValue().get("Uncompleted"));
        }
    }
}

