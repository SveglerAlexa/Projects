package Presentation.View;
import Model.Bill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * BillView is a GUI component that displays a list of Bill objects in a table format.
 * It provides a scrollable JTable that shows the fields: ID, Client, Product, Quantity, and Total Price.
 */


public class BillView extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Constructs a BillView panel with a table that displays bill data.
     * The table includes columns for ID, Client, Product, Quantity, and Total Price.
     */


    public BillView() {

        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Client", "Product", "Quantity", "Total Price"};

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

    }

    /**
     * Populates the table with a list of Bill objects.
     * Each bill is displayed as a row with values for ID, Client, Product, Quantity, and Total Price.
     *
     * @param bills the list of Bill objects to be displayed in the table
     */


    public void setData(List<Bill> bills) {
        tableModel.setRowCount(0); // clear

        for (Bill b : bills) {
            Object[] row = {
                    b.id(),
                    b.clientName(),
                    b.productName(),
                    b.quantity(),
                    b.totalPrice()
            };
            tableModel.addRow(row);
        }
    }
}

