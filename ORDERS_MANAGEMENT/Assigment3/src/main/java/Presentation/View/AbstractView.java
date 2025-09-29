package Presentation.View;

import DataAccess.AbstractDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Logger;
import java.util.List;

/**
 * The AbstractView class is an abstract generic user interface component used for displaying
 * a list of objects of type T in a JTable. It dynamically generates the table columns
 * based on the fields declared in the generic type T using Java Reflection.
 * The class provides:
 * - A scrollable JTable to display object data.
 * - A method to populate the table with a list of T instances.
 * @param <T> the type of objects to be displayed in the view
 */


public abstract class AbstractView<T> extends JPanel {

    protected static final Logger LOGGER = Logger.getLogger(AbstractView.class.getName());

    private JTable table;
    private DefaultTableModel tableModel;
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractView(Class<T> type) {
        this.type = type;
        ///cream un tabel model cu coloane dupa numele campurilor clasei T
        String[] columnNames = getFieldNames();
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

    }

    private String[] getFieldNames() {
        Field[] fields = type.getDeclaredFields();
        String[] names = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            names[i] = fields[i].getName();
        }
        return names;
    }

    public void setData(List<T> data) {
        tableModel.setRowCount(0);
        for (T item : data) {
            Object[] rowData = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < rowData.length; i++) {
                try {
                    Field field = type.getDeclaredFields()[i];
                    field.setAccessible(true);
                    rowData[i] = field.get(item);
                } catch (IllegalAccessException e) {
                    rowData[i] = "N/A";
                }
            }
            tableModel.addRow(rowData);
        }
    }

}
