package org.example.presentation;
import org.example.bussinesslogic.TasksManagement;
import org.example.bussinesslogic.Utility;
import org.example.dataaccess.LoadSave;
import org.example.datamodel.ComplexTask;
import org.example.datamodel.Employee;
import org.example.datamodel.SimpleTask;
import org.example.datamodel.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Interfata extends JFrame implements ActionListener, Serializable {

    JFrame frame;
    ///Paneluri
    JPanel panel = new JPanel();
    JPanel butoane = new JPanel();
    JPanel centru = new JPanel(); // Panou care conține caracteristic + afisare
    JPanel caracteristic = new JPanel();
    JPanel afisare = new JPanel();
    JPanel task= new JPanel();
    JPanel employeesPanel = new JPanel();
    JPanel assignPanel = new JPanel();
    JPanel afisareButoane = new JPanel();
    JPanel afisareText = new JPanel();
    ///Butoane
    JButton btnAddEmployee= new JButton("Add Employee");
    JButton btnAddSimpleTask = new JButton("Add Simple Task");
    JButton btnAddComplexTask = new JButton("Add Complex Task");
    JButton btnViewEmployee = new JButton("View Employee");
    JButton btnViewStatistics = new JButton("  Statistics   ");
    JButton btnAddSubtask = new JButton("Add Subtask");
    //JButton btnRemoveSubtask = new JButton("Remove Subtask");
    JButton btnAssignTask = new JButton("Assign Task");
    JButton btnTasks=new JButton("View Tasks");
    JButton btnViewTasksAssigned = new JButton("View Tasks Assigned");
    JButton btnModifyStatus = new JButton("Modify Status");
    ///Label-uri
    JLabel lAddEmployee = new JLabel("ADD EMPLOYEE:");
    JLabel lIdEmp= new JLabel("ID Employee:");
    JLabel lNameEmp= new JLabel("Name Employee:");
    JLabel lSpatiu = new JLabel(" ");
    JLabel lAddTask = new JLabel("ADD TASK:");
    JLabel lIdTask= new JLabel("ID Task:");
    JLabel lStarHour = new JLabel("Star Hour:");
    JLabel lEndHour = new JLabel("End Hour:");
    JLabel lStatus= new JLabel("Choose Task Status:");
    JLabel lAddSubtask= new JLabel("Add Subtask:");
    JLabel lIdSubtask= new JLabel("ID Subtask:");
    JLabel lAssignTask= new JLabel("ASSIGN TASK TO EMPLOYEE:");
    JLabel lAssignEmployee= new JLabel("ID Employee:");
    JLabel lAssignTaskToEmployee= new JLabel("ID Task:");
    JLabel lModifyStatus= new JLabel("MODIFY STATUS:");
    JLabel lModifyEmployee= new JLabel("ID Employee:");
    JLabel lModifyTask= new JLabel("ID Task:");
    ///TextField
    JTextField textIdEmp= new JTextField();
    JTextField textNameEmp= new JTextField();
    JTextField textIdTask= new JTextField();
    JTextField textStarHour= new JTextField();
    JTextField textEndHour= new JTextField();
    JTextField textIdSubtask= new JTextField();
    JTextField textEmployee= new JTextField();
    JTextField textAssignTask= new JTextField();
    JTextField textModifyEmployee= new JTextField();
    JTextField textModifyTask= new JTextField();

    ///ScrollPane
    JScrollPane scrollPane= new JScrollPane(afisare);
    JTextArea textArea= new JTextArea();

    ///ComboBox
    JComboBox comboBox= new JComboBox();
    JComboBox status= new JComboBox();

    ///
    ArrayList<Employee> employees= new ArrayList<>();
    ArrayList<Task> allTasks= new ArrayList<>();
    TasksManagement manage= new TasksManagement();
    Map<Employee, ArrayList<Task>> display= new HashMap<>();
    Utility statistici= new Utility();
    private Map<String,Map<String,Integer>> nrTaskuri=new HashMap<>();

    ///culori
    Color scrisButon = new Color(230, 215, 216);
    Color butoaneColor = new Color(120, 100, 90);
    Color fundal= new Color(220, 210, 200);//new Color(245, 222, 224);
    Color afisareColor=new Color(200, 190, 180);
    Color subButoaneColor =new Color(150, 130, 120);
    public Interfata() {
        frame = new JFrame("Task Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Dimensiunea ferestrei


        // Layout principal
        panel.setLayout(new BorderLayout());
        // Layout pentru container (2 coloane egale)
        centru.setLayout(new GridLayout(1, 2));
        //SUS
        butoane.setLayout(new GridLayout(1, 2));
        // Dimensiuni pentru butoane (sus)
        butoane.setPreferredSize(new Dimension(600, 50));


        //dreapta:
        centru.add(caracteristic);
        caracteristic.setLayout(new GridLayout(3, 1));
        employeesPanel.setLayout(new GridLayout(8, 1));
        employeesPanel.setBackground(fundal);
        task.setLayout(new GridLayout(8, 10));
        task.setBackground(fundal);
        assignPanel.setLayout(new GridLayout(12, 1));
        assignPanel.setBackground(fundal);
        caracteristic.add(employeesPanel);
        caracteristic.add(task);
        caracteristic.add(assignPanel);

        //stanga
        afisare.setLayout(new BorderLayout());
        centru.add(afisare);
        afisare.add(afisareButoane, BorderLayout.NORTH);
        afisare.add(afisareText, BorderLayout.CENTER);
        afisareText.setBackground(fundal);
        afisareButoane.setLayout(new GridLayout(1,5));
        afisareButoane.setBackground(fundal);
        afisareButoane.setPreferredSize(new Dimension(600, 50));
        // Adaugam butoanele sus si containerul jos
        panel.add(butoane, BorderLayout.NORTH);
        panel.add(centru, BorderLayout.CENTER);


        frame.setContentPane(panel);
        frame.setVisible(true);


        btnViewTasksAssigned.setBackground(subButoaneColor);
        btnViewTasksAssigned.setBorder(BorderFactory.createLineBorder(Color.BLACK));

//        btnRemoveSubtask.setBackground(butoaneColor);
//        btnRemoveSubtask.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        btnRemoveSubtask.setForeground(scrisButon);

        btnAddEmployee.setBackground(butoaneColor);
        btnAddEmployee.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnAddEmployee.setForeground(scrisButon);

        btnAddSimpleTask.setBackground(butoaneColor);
        btnAddSimpleTask.setForeground(scrisButon);
        btnAddSimpleTask.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btnAddComplexTask.setBackground(butoaneColor);
        btnAddComplexTask.setForeground(scrisButon);
        btnAddComplexTask.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btnViewEmployee.setBackground(subButoaneColor);
        btnViewEmployee.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btnViewStatistics.setBackground(subButoaneColor);
        btnViewStatistics.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btnAddSubtask.setBackground(butoaneColor);
        btnAddSubtask.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnAddSubtask.setForeground(scrisButon);

        btnAssignTask.setBackground(butoaneColor);
        btnAssignTask.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnAssignTask.setForeground(scrisButon);

        btnModifyStatus.setBackground(subButoaneColor);
        btnModifyStatus.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btnTasks.setBackground(subButoaneColor);
        btnTasks.setBorder(BorderFactory.createLineBorder(Color.BLACK));



        ///PANELUL-BUTOANE
        butoane.add(btnAddEmployee);
        butoane.add(btnAddSimpleTask);
        butoane.add(btnAddComplexTask);
        butoane.add(btnAddSubtask);
        //butoane.add(btnRemoveSubtask);
        butoane.add(btnAssignTask);

        btnAddEmployee.addActionListener(this);
        btnAddSimpleTask.addActionListener(this);
        btnAddComplexTask.addActionListener(this);
        btnAddSubtask.addActionListener(this);
       // btnRemoveSubtask.addActionListener(this);
        btnAssignTask.addActionListener(this);


        ///PANELUL-EMPLOYEES
        employeesPanel.add(lAddEmployee);
        employeesPanel.add(lIdEmp);
        employeesPanel.add(textIdEmp);
        employeesPanel.add(lNameEmp);
        employeesPanel.add(textNameEmp);
        employeesPanel.add(lAddTask);
        employeesPanel.add(comboBox);

        comboBox.addItem("Simple Task");
        comboBox.addItem("Complex Task");
        comboBox.addActionListener(this);
        status.addItem("Completed");
        status.addItem("Uncompleted");

        ///PANELUL-ASSIGN
        assignPanel.add(lAssignTask);
        assignPanel.add(lAssignEmployee);
        assignPanel.add(textEmployee);
        assignPanel.add(lAssignTaskToEmployee);
        assignPanel.add(textAssignTask);
        assignPanel.add(lModifyStatus);
        assignPanel.add(lModifyEmployee);
        assignPanel.add(textModifyEmployee);
        assignPanel.add(lModifyTask);
        assignPanel.add(textModifyTask);

        ///PANELUL-TASK
        task.add(lIdTask);
        task.add(textIdTask);
        task.add(lStatus);
        task.add(status);

        ///PANELUL-AFISARE
        afisareButoane.add(btnViewEmployee);
        afisareButoane.add(btnViewStatistics);
        afisareButoane.add(btnTasks);
        afisareButoane.add(btnViewTasksAssigned);
        afisareButoane.add(btnModifyStatus);
        btnViewEmployee.addActionListener(this);
        btnViewStatistics.addActionListener(this);
        btnTasks.addActionListener(this);
        btnViewTasksAssigned.addActionListener(this);
        btnModifyStatus.addActionListener(this);
        afisareText.add(scrollPane);
        scrollPane.setViewportView(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 650));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollPane.setBackground(afisareColor);
        textArea.setBackground(afisareColor);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnAddEmployee) {
            int id = Integer.parseInt(textIdEmp.getText());
            String name = textNameEmp.getText();
            Employee employee= new Employee(id, name);
            manage.addEmployee(employee);

            employees=manage.getAllEmployees();
            textArea.setText("");
            for(Employee emp: employees) {
                textArea.append(emp.toString() + "\n");
            }


        }
        else if(e.getSource() == btnViewEmployee) {
            textArea.setText("");
            display=manage.getMap();
            for(Map.Entry<Employee,ArrayList<Task>> entry: display.entrySet())
            {
                Employee employee = entry.getKey();
                textArea.append(employee.toString() + "\n");
            }

        }
        else if(e.getSource()== btnTasks) {
            allTasks=manage.getAllTasks();
            textArea.setText("");
            for(Task task: allTasks) {
                textArea.append(task.toString() + "\n");

            }

        }
        else if(e.getSource() == comboBox) {
            String comboBoxValue = comboBox.getSelectedItem().toString();
            if(comboBoxValue.equals("Simple Task")) {

                task.remove(lAddSubtask);
                task.remove(lIdSubtask);
                task.remove(textIdSubtask);

                task.add(lStarHour);
                task.add(textStarHour);
                task.add(lEndHour);
                task.add(textEndHour);

                task.revalidate(); // Refacem layout-ul
                task.repaint(); // Redesenam interfața

            }
            else if(comboBoxValue.equals("Complex Task")) {

                task.remove(lStarHour);
                task.remove(textStarHour);
                task.remove(lEndHour);
                task.remove(textEndHour);

                task.add(lAddSubtask);
                task.add(lIdSubtask);
                task.add(textIdSubtask);

                task.revalidate(); // Refacem layout-ul
                task.repaint(); // Redesenam interfața

            }

        }
        else if(e.getSource() == btnAddSimpleTask) {
            int id=Integer.parseInt(textIdTask.getText());
            String statusul=status.getSelectedItem().toString();
            int startHour = Integer.parseInt(textStarHour.getText());
            int endHour = Integer.parseInt(textEndHour.getText());

            SimpleTask simpleTask= new SimpleTask(startHour,endHour,id,statusul);
            manage.addTask(simpleTask);
            allTasks=manage.getAllTasks();
            textArea.setText("");
            for(Task task: allTasks) {
                textArea.append(task.toString() + "\n");

            }
        }
        else if(e.getSource() == btnAddComplexTask) {
            int id=Integer.parseInt(textIdTask.getText());
            String statusul=status.getSelectedItem().toString();
            ComplexTask complexTask= new ComplexTask(id,statusul);
            manage.addTask(complexTask);

            allTasks=manage.getAllTasks();
            textArea.setText("");
            for(Task task: allTasks) {
                textArea.append(task.toString() + "\n");

            }

        }
        else if(e.getSource() == btnAddSubtask) {
            int id=Integer.parseInt(textIdSubtask.getText());
            int complexTaskId=Integer.parseInt(textIdTask.getText());
            manage.verificareAdaugare(complexTaskId,id);
             allTasks=manage.getAllTasks();
            textArea.setText("");
            for(Task task: allTasks) {
                textArea.append(task.toString() + "\n");
            }


        }
//        else if(e.getSource()==btnRemoveSubtask)
//        {
//            int id=Integer.parseInt(textIdSubtask.getText());
//            int complexTaskId=Integer.parseInt(textIdTask.getText());
//           // ((ComplexTask)manage.returnTask(complexTaskId)).verificareStergere(manage.getAllTasks(),id);
//            manage.verificareStergere(complexTaskId,id);
//            manage.afisareTaskuri(); allTasks=manage.getAllTasks();
//            textArea.setText("");
//            for(Task task: allTasks) {
//                textArea.append(task.toString() + "\n");
//            }
//
//        }
        else if(e.getSource()==btnAssignTask) {
            int idEmployee=Integer.parseInt(textEmployee.getText());
            int idTask=Integer.parseInt(textAssignTask.getText());
            manage.assignTaskToEmployee(idEmployee,idTask);

            textArea.setText("");
            display=manage.getMap();
            for(Map.Entry<Employee,ArrayList<Task>> entry: display.entrySet())
            {
                Employee employee = entry.getKey();
                textArea.append(employee.getName()+", id: "+ employee.getIdEmployee() + "\n"+"Taskuri:\n");
                System.out.println(employee);
                ArrayList<Task> tasks = entry.getValue();
                for(Task t: tasks)
                {
                    textArea.append(t.toString() + "\n");
                    System.out.println(t.toString());
                }
            }

        }
        else if(e.getSource()==btnViewTasksAssigned)
        {
            textArea.setText("");
            display=manage.getMap();
            for(Map.Entry<Employee,ArrayList<Task>> entry: display.entrySet())
            {
                Employee employee = entry.getKey();
                textArea.append(employee.getName()+", id: "+ employee.getIdEmployee() + "\n"+"Tasks:\n");
                ArrayList<Task> tasks = entry.getValue();
                for(Task t: tasks)
                {
                    textArea.append(t + "\n");

                }
            }


        }
        else if(e.getSource() == btnViewStatistics) {
            //employees=null;
            employees=statistici.workDuration40(manage);
            textArea.setText("");
            textArea.append("Employees who have a worduration greater than 40 hours:\n");
            for(Employee employee: employees) {
                textArea.append(employee.toString() + "\n");
            }

            textArea.append("\nThe number of completed and uncompleted tasks:\n");
            statistici.numberOfTasks(manage);
            nrTaskuri=statistici.getMap();
            for (Map.Entry<String, Map<String, Integer>> entry : nrTaskuri.entrySet()) {
                textArea.append("Employee: " + entry.getKey());
                textArea.append("  Completed: " + entry.getValue().get("Completed"));
                textArea.append("  Uncompleted: " + entry.getValue().get("Uncompleted"));
                textArea.append("\n");
            }



        }
        else if(e.getSource()==btnModifyStatus)
        {
            int idEmployee=Integer.parseInt(textModifyEmployee.getText());
            int idTask=Integer.parseInt(textModifyTask.getText());
            manage.modifyTaskStatus(idEmployee,idTask);
        }

    }

    public static void main(String[] args) {
        new Interfata();
    }
}


