package Presentation.Controller;

import BusinessLogic.ClientBLL;
import Model.Client;
import Presentation.View.ClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClientController handles the interactions between the ClientView (UI) and the ClientBLL (business logic).
 * It manages client-related operations such as adding, updating, and deleting clients.
 * It also updates the OrderController whenever the client list is changed, to ensure synchronized data.
 */

public class ClientContoller {

    private ClientView clientView;
    private ClientBLL clientBLL;
    private OrderController orderController;


    /**
     * Constructs a ClientController with the specified ClientView and OrderController.
     * Initializes the view with existing client data and sets up action listeners for the client buttons.
     *
     * @param view the ClientView that contains the UI components for managing clients
     * @param orderController the OrderController that uses client data (e.g., for orders)
     */


    public ClientContoller(ClientView view,OrderController orderController) {
        this.clientView = view;
        this.clientBLL = new ClientBLL();
        this.orderController = orderController;

        clientView.setData(clientBLL.findAll());

        this.clientView.getBtnAddClient().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
                orderController.loadClients();///reincarca comboboxul
            }
        });

        this.clientView.getBtnUpdateClient().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateClient();
            }

        });

        this.clientView.getBtnDeleteClient().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
                orderController.loadClients();///reincarca comboboxul
            }

        });
    }

    /**
     * Reads input data from the ClientView, creates a new Client object, and adds it using ClientBLL.
     * Refreshes the client table and updates the order controller's client data.
     * Displays error messages in case of invalid input.
     */


    private void addClient() {
        try {
            int id = Integer.parseInt(clientView.getClientIDT().getText());
            String name = clientView.getNameT().getText();
            String email = clientView.getEmailT().getText();
            int age = Integer.parseInt(clientView.getAgeT().getText());
            String address = clientView.getAddressT().getText();


            Client client = new Client(id, name, email, age, address);
            clientBLL.insert(client);

            clientView.setData(clientBLL.findAll());

            System.out.println("Client added successfully!");

        } catch (NumberFormatException ex) {
            System.out.println("Invalid number format!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Reads updated client data from the ClientView and sends it to ClientBLL for update.
     * Refreshes the client table and shows a confirmation message on success.
     * Displays error messages in case of invalid input.
     */


    private void updateClient() {
        try {
            int id = Integer.parseInt(clientView.getClientIDT().getText());
            String name = clientView.getNameT().getText();
            String email = clientView.getEmailT().getText();
            int age = Integer.parseInt(clientView.getAgeT().getText());
            String address = clientView.getAddressT().getText();

            Client updatedClient = new Client(id, name, email, age, address);
            clientBLL.update(updatedClient);

            JOptionPane.showMessageDialog(clientView, "Client updated successfully!");
            clientView.setData(clientBLL.findAll());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(clientView, "ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Retrieves the client data from the ClientView and deletes the corresponding client using ClientBLL.
     * Updates the client table and the order controller's client list, and shows a confirmation message.
     */


    private void deleteClient() {
        int id = Integer.parseInt(clientView.getClientIDT().getText());
        String name = clientView.getNameT().getText();
        String email = clientView.getEmailT().getText();
        int age = Integer.parseInt(clientView.getAgeT().getText());
        String address = clientView.getAddressT().getText();

        Client deletedClient = new Client(id, name, email, age, address);
        clientBLL.delete(deletedClient);
        JOptionPane.showMessageDialog(clientView, "Client deleted successfully!");
        clientView.setData(clientBLL.findAll());

    }
}


