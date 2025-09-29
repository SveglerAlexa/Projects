package org.example.dataaccess;


import org.example.datamodel.Employee;
import org.example.datamodel.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadSave {

    private static final String TASK = "allTasks.dat";
    private static final String MAP = "map.dat";
    private static final String EMPLOYEE = "employee.dat";


    public static void saveMap(Map<Employee, ArrayList<Task>> taskMap){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(MAP))) {
            out.writeObject(taskMap); //scrie obiectul in fisier ,,FileOutputStream deschide fisierul
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Employee, ArrayList<Task>> loadMap() {
        File file = new File(MAP);
        if (!file.exists()) return new HashMap<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(MAP))) {
            return (Map<Employee, ArrayList<Task>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void saveAllTasks(ArrayList<Task> tasks){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(TASK))){
            out.writeObject(tasks);
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Task> loadAllTasks(){
        File file = new File(TASK);
        if(!file.exists())
            return new ArrayList<>();

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(TASK))){
            return (ArrayList<Task>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static void saveEmployees(ArrayList<Employee> employees){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(EMPLOYEE))){
            out.writeObject(employees);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Employee> loadEmployees(){
        File file = new File(EMPLOYEE);
        if(!file.exists())
            return new ArrayList<Employee>();

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(EMPLOYEE))) {
            return (ArrayList<Employee>) in.readObject();
        } catch (IOException| ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }






}
