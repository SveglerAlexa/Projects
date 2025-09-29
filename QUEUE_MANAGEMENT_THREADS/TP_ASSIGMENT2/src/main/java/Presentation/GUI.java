package Presentation;

import BuisnessLogic.Logger;
import BuisnessLogic.SimulationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class GUI extends JFrame implements ActionListener {

    ///Paneluri
    JPanel panel = new JPanel();
    JPanel centru = new JPanel();
    JPanel afisare=new JPanel();
    JPanel cerinte=new JPanel();

    ///ScrollPane
    JScrollPane scrollPane= new JScrollPane(afisare);
    JTextArea textArea= new JTextArea();


    ///Label-uri
    JLabel lNrClients=new JLabel("Number of Clients:");
    JLabel lNrQueues=new JLabel("Number of Queues:");
    JLabel lSimulationTime=new JLabel("Simulation Time:");
    JLabel lMinArrivalTime=new JLabel("Minimum Arrival Time:");
    JLabel lMaxArrivalTime=new JLabel("Maximum Arrival Time:");
    JLabel lMinServiceTime=new JLabel("Minimum Service Time:");
    JLabel lMaxServiceTime=new JLabel("Maximum Service Time:");
    JLabel lSlectionPolicy=new JLabel("Slection Policy:");

    ///TextField
    JTextField textNrClients=new JTextField();
    JTextField textNrQueues=new JTextField();
    JTextField textSimulationTime=new JTextField();
    JTextField textMinArrivalTime=new JTextField();
    JTextField textMaxArrivalTime=new JTextField();
    JTextField textMinServiceTime=new JTextField();
    JTextField textMaxServiceTime=new JTextField();

    JComboBox comboBox = new JComboBox();


    ///culori
    Color scrisButon = new Color(230, 215, 216);
    Color butoaneColor = new Color(120, 100, 90);
    Color fundal= new Color(220, 210, 200);//new Color(245, 222, 224);
    Color afisareColor=new Color(200, 190, 180);
    Color subButoaneColor =new Color(150, 130, 120);

    JButton start = new JButton("Start Simulation");

    public GUI() {
        JFrame frame = new JFrame("Queues Managemant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Dimensiunea ferestrei

        //Layout principal
        panel.setLayout(new BorderLayout());
        panel.add(centru,BorderLayout.CENTER);
        panel.add(start,BorderLayout.SOUTH);

        start.setBackground(butoaneColor);
        start.setForeground(scrisButon);
        start.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        centru.setLayout(new GridLayout(1,2));
        centru.add(cerinte);
        centru.add(afisare);

        cerinte.setBackground(fundal);
        cerinte.setLayout(new GridLayout(17,1));
        afisare.setBackground(fundal);

        frame.setContentPane(panel);
        frame.setVisible(true);


        ///PANELUL-CERINTE

        cerinte.add(lNrClients);
        cerinte.add(textNrClients);
        cerinte.add(lNrQueues);
        cerinte.add(textNrQueues);
        cerinte.add(lSimulationTime);
        cerinte.add(textSimulationTime);
        cerinte.add(lMinArrivalTime);
        cerinte.add(textMinArrivalTime);
        cerinte.add(lMaxArrivalTime);
        cerinte.add(textMaxArrivalTime);
        cerinte.add(lMinServiceTime);
        cerinte.add(textMinServiceTime);
        cerinte.add(lMaxServiceTime);
        cerinte.add(textMaxServiceTime);


        comboBox.addItem("SHORTEST_QUEUE");
        comboBox.addItem("SHORTEST_TIME");

        cerinte.add(lSlectionPolicy);
        cerinte.add(comboBox);

        afisare.add(scrollPane);
        scrollPane.setViewportView(textArea);
        scrollPane.setPreferredSize(new Dimension(650, 700));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea.setBackground(afisareColor);

        Logger logger = Logger.getInstance();
        logger.setTextArea(textArea);

        logger.setWriteToConsole(true);
        logger.setWriteToFile(false);

        start.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == start) {
            int nrClients=Integer.parseInt(textNrClients.getText());
            int nrQueues=Integer.parseInt(textNrQueues.getText());
            int nrSimulationTime=Integer.parseInt(textSimulationTime.getText());
            int nrMinArrivalTime=Integer.parseInt(textMinArrivalTime.getText());
            int nrMaxArrivalTime=Integer.parseInt(textMaxArrivalTime.getText());
            int nrMinServiceTime=Integer.parseInt(textMinServiceTime.getText());
            int nrMaxServiceTime=Integer.parseInt(textMaxServiceTime.getText());
            String selection=comboBox.getSelectedItem().toString();
            textArea.setText("");


            SimulationManager simulationManager=new SimulationManager(nrSimulationTime,nrClients,nrMaxServiceTime,nrMinServiceTime,nrMinArrivalTime,nrMaxArrivalTime,nrQueues,selection);
            Thread simulationThread =new Thread(simulationManager);
            simulationThread.start();
        }

    }

    public static void main(String[] args) {
        new GUI();
    }
}
