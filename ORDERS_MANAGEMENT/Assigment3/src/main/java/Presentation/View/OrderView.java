package Presentation.View;

import Model.Orders;

import javax.swing.*;
import java.awt.*;

/**
 * The OrderView class represents the graphical user interface (GUI) component
 * for managing Order entities. I
 */

public class OrderView extends AbstractView <Orders>{

    private JPanel panel;
    JButton btnAddOrder;
    private JComboBox<Integer> clientComboBox;
    private JComboBox<Integer> productComboBox;
    JTextField quantityT;
    JTextField orderIDT;
    public OrderView() {
        super(Orders.class);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        btnAddOrder = new JButton("Add Order");


        JLabel orderIDL = new JLabel("OrderID:");
        orderIDT = new JTextField();

        JLabel clientNameL = new JLabel("Client ID:");
        clientComboBox = new JComboBox<>();

        JLabel productName = new JLabel("Product ID:");
        productComboBox = new JComboBox<>();

        JLabel quantityL = new JLabel("Quantity:");
        quantityT = new JTextField();



        panel.add(orderIDL);
        panel.add(orderIDT);
        panel.add(clientNameL);
        panel.add(clientComboBox);
        panel.add(productName);
        panel.add(productComboBox);
        panel.add(quantityL);
        panel.add(quantityT);

        panel.add(btnAddOrder);

    }

    public JButton getBtnAddOrder() {
        return btnAddOrder;
    }

    public JComboBox<Integer> getClientComboBox() {
        return clientComboBox;
    }

        public JComboBox<Integer> getProductComboBox() {
        return productComboBox;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getQuantityT() {
        return quantityT;
    }

    public JTextField getOrderIDT() {
        return orderIDT;
    }
}
