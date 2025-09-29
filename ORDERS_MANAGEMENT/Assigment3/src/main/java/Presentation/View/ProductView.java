package Presentation.View;

import Model.Product;

import javax.swing.*;
import java.awt.*;

/**
 * The ProductView class represents the graphical user interface (GUI) component
 * for managing Product entities. I
 */


public class ProductView extends AbstractView<Product> {

    private JPanel panel;
    JButton btnAddProduct;
    JButton btnUpdateProduct;
    JButton btnDeleteProduct;
    JTextField productIDT;
    JTextField productnameT;
    JTextField priceT;
    JTextField quantityT;
    public ProductView() {
        super(Product.class);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        btnAddProduct = new JButton("Add Product");
        btnUpdateProduct = new JButton("Update Product");
        btnDeleteProduct = new JButton("Delete Product");

        JLabel productIDL = new JLabel("ProductID:");
        productIDT = new JTextField();

        JLabel productName = new JLabel("Product Name:");
        productnameT = new JTextField();

        JLabel priceL = new JLabel("Price:");
        priceT = new JTextField();

        JLabel quantityL = new JLabel("Quantity:");
        quantityT = new JTextField();



        panel.add(productIDL);
        panel.add(productIDT);
        panel.add(productName);
        panel.add(productnameT);
        panel.add(priceL);
        panel.add(priceT);
        panel.add(quantityL);
        panel.add(quantityT);

        panel.add(btnAddProduct);
        panel.add(btnUpdateProduct);
        panel.add(btnDeleteProduct);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextField getProductIDT() {
        return productIDT;
    }

    public JTextField getProductnameT() {
        return productnameT;
    }

    public JTextField getPriceT() {
        return priceT;
    }

    public JTextField getQuantityT() {
        return quantityT;
    }

    public JButton getBtnAddProduct() {
        return btnAddProduct;
    }

    public JButton getBtnUpdateProduct() {
        return btnUpdateProduct;
    }

    public JButton getBtnDeleteProduct() {
        return btnDeleteProduct;
    }
}
