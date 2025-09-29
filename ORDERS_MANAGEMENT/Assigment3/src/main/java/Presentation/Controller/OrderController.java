package Presentation.Controller;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Orders;
import Model.Product;
import Presentation.View.OrderView;
import Presentation.View.ProductView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

/**
 * The OrderController class is responsible for handling user interactions related to placing orders.
 * It communicates with the OrderView to get user input and with the business logic (BLL) layer to perform operations.
 * It also manages the synchronization of clients, products, and order data across the UI.
 */


public class OrderController {

    private OrderView orderView;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private OrderBLL orderBLL;
    private ProductController productController;
    private BillController billController;

    /**
     * Constructs an OrderController with the given OrderView and BillController.
     * Initializes the view with current client and product data and sets up the action listener for adding orders.
     *
     * @param view the OrderView used to interact with the user interface for orders
     * @param billController the BillController used to handle bill generation after an order is placed
     */


    public OrderController(OrderView view,BillController billController) {
        this.orderView = view;
        this.clientBLL = new ClientBLL();
        this.productBLL = new ProductBLL();
        this.orderBLL = new OrderBLL();
        this.billController = billController;


        loadClients();
        loadProducts();

        orderView.setData(orderBLL.findAll());
        orderView.getBtnAddOrder().addActionListener(e -> addOrder());

    }

    /**
     * Loads all clients from the database and populates the client combo box in the OrderView.
     */

    public void loadClients() {
        List<Client> clients = clientBLL.findAll();
        JComboBox<Integer> combo = orderView.getClientComboBox();
        combo.removeAllItems();
        for (Client c : clients) {
            combo.addItem(c.getId());
        }
    }

    /**
     * Loads all products from the database and populates the product combo box in the OrderView.
     * Called after any product update to ensure combo box accuracy.
     */


    public void loadProducts() {
        List<Product> products = productBLL.findAll();
        JComboBox<Integer> combo = orderView.getProductComboBox();
        combo.removeAllItems();
        for (Product p : products) {
            combo.addItem(p.getId()); // sau alt camp identificator
        }


    }

    /**
     * Retrieves order details from the OrderView, creates an Orders object, and inserts it via OrderBLL.
     * Updates the order table and product stock, refreshes the view, and handles any validation or SQL errors.
     * Also generates a bill through the BillController after successful order placement.
     */


    public void addOrder() {

        try {
            int id = Integer.parseInt(orderView.getOrderIDT().getText());
            int clientId = (Integer) orderView.getClientComboBox().getSelectedItem();
            int productId = (Integer) orderView.getProductComboBox().getSelectedItem();
            int quantity = Integer.parseInt(orderView.getQuantityT().getText());


            Orders order = new Orders(id, clientId, productId, quantity);
            orderBLL.insert(order,billController);

            orderView.setData(orderBLL.findAll());
            productController.refreshProducts();


            loadProducts();


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(orderView.getPanel(), "Invalid input!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }catch (IllegalArgumentException e) {
            // Aici prinzi erorile de business, inclusiv stoc insuficient
            JOptionPane.showMessageDialog(orderView, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the ProductController reference to allow the OrderController to refresh the product list after placing an order.
     *
     * @param productController the controller responsible for managing product data and UI
     */


    public void setProductController(ProductController productController) {
        this.productController = productController;
    }
}

