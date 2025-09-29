package Presentation.View;

import Model.Client;
import javax.swing.*;

/**
 * The ClientView class represents the graphical user interface (GUI) component
 * for managing Client entities. I
 */

public class ClientView  extends AbstractView <Client>{

    private JPanel panel;
    JButton btnAddClient;
    JButton btnUpdateClient;
    JButton btnDeleteClient;
    JTextField clientIDT;
    JTextField nameT;
    JTextField emailT;
    JTextField ageT;
    JTextField addressT;
    public ClientView() {
        super(Client.class);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        btnAddClient = new JButton("Add Client");
        btnUpdateClient = new JButton("Update Client");
        btnDeleteClient = new JButton("Delete Client");

        JLabel clientIDL = new JLabel("ClientID:");
        clientIDT = new JTextField();

        JLabel nameL = new JLabel("Name:");
        nameT = new JTextField();

        JLabel emailL = new JLabel("Email:");
        emailT = new JTextField();

        JLabel ageL = new JLabel("Age:");
        ageT = new JTextField();

        JLabel addressL = new JLabel("Address:");
        addressT = new JTextField();

        panel.add(clientIDL);
        panel.add(clientIDT);
        panel.add(nameL);
        panel.add(nameT);
        panel.add(emailL);
        panel.add(emailT);
        panel.add(ageL);
        panel.add(ageT);
        panel.add(addressL);
        panel.add(addressT);

        panel.add(btnAddClient);
        panel.add(btnUpdateClient);
        panel.add(btnDeleteClient);



    }

    public  JPanel getPanel() {
        return panel;
    }

    public JButton getBtnAddClient() {
        return btnAddClient;
    }

    public JButton getBtnUpdateClient() {
        return btnUpdateClient;
    }

    public JButton getBtnDeleteClient() {
        return btnDeleteClient;
    }

    public JTextField getClientIDT() {
        return clientIDT;
    }

    public JTextField getNameT() {
        return nameT;
    }

    public JTextField getEmailT() {
        return emailT;
    }

    public JTextField getAgeT() {
        return ageT;
    }

    public JTextField getAddressT() {
        return addressT;
    }




}
