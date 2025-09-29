package Presentation.Controller;

import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;
import Presentation.View.ProductView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ProductController is responsible for handling user interactions related to products.
 * It connects the ProductView (GUI) with the ProductBLL (business logic) to perform operations such as
 * adding, updating, and deleting products.
 * It also notifies the OrderController when product data is changed, so the order view stays updated.
 */


public class ProductController {

    private ProductView productView;
    private ProductBLL productBLL;
    private OrderController orderController;

    /**
     * Constructs a ProductController with a given ProductView and OrderController.
     * It sets up listeners for the Add, Update, and Delete buttons in the ProductView.
     *
     * @param view the ProductView that contains the product UI components
     * @param orderController the OrderController that needs to refresh product data in its view
     */


    public ProductController(ProductView view,OrderController orderController) {
        this.productView = view;
        this.productBLL = new ProductBLL();
        this.orderController = orderController;

        productView.setData(productBLL.findAll());

        this.productView.getBtnAddProduct().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
                orderController.loadProducts();//reinacrca combo-Boxul din OrderView
            }
        });

        this.productView.getBtnUpdateProduct().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }

        });

        this.productView.getBtnDeleteProduct().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
                orderController.loadProducts();//reinacrca combo-Boxul din OrderView
            }
        });
    }

    /**
     * Gathers input from the ProductView fields and adds a new product using ProductBLL.
     * Displays a confirmation or error message and refreshes the product table and order view.
     */


    private void addProduct() {
            try {
                int id = Integer.parseInt(productView.getProductIDT().getText());
                String name = productView.getProductnameT().getText();
                int price = Integer.parseInt(productView.getPriceT().getText());
                int quantity = Integer.parseInt(productView.getQuantityT().getText());

                Product product = new Product(id, name, price, quantity);
                productBLL.insert(product);

                System.out.println("Product added successfully!");
                productView.setData(productBLL.findAll());


            } catch (NumberFormatException ex) {
                System.out.println("Invalid number format!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    /**
     * Reads input fields from the ProductView and updates the corresponding product using ProductBLL.
     * Displays a success or error message and refreshes the table data in the view.
     */


    private void updateProduct() {

            try{
                int id = Integer.parseInt(productView.getProductIDT().getText());
                String name = productView.getProductnameT().getText();
                int price = Integer.parseInt(productView.getPriceT().getText());
                int quantity = Integer.parseInt(productView.getQuantityT().getText());

                Product updatedClient = new Product(id, name, price,quantity);
                productBLL.update(updatedClient);

                JOptionPane.showMessageDialog(productView, "Product updated successfully!");
                productView.setData(productBLL.findAll());

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(productView, "ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }

    /**
     * Reads the product data from the ProductView and deletes the corresponding product using ProductBLL.
     * Shows a confirmation message and updates both the product table and the order view.
     */


    private void deleteProduct() {
            int id = Integer.parseInt(productView.getProductIDT().getText());
            String name = productView.getProductnameT().getText();
            int price = Integer.parseInt(productView.getPriceT().getText());
            int quantity = Integer.parseInt(productView.getQuantityT().getText());

            Product deletedClient = new Product(id, name, price,quantity);
            productBLL.delete(deletedClient);
            JOptionPane.showMessageDialog(productView, "Product deleted successfully!");
            productView.setData(productBLL.findAll());

        }


    /**
     * Refreshes the product table in the ProductView by reloading all products from the database.
     */

    public void refreshProducts() {
        productView.setData(productBLL.findAll());
    }


}

