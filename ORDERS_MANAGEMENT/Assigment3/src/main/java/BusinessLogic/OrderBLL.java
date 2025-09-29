package BusinessLogic;

import DataAccess.BillDAO;
import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.*;
import Presentation.Controller.BillController;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import Model.Client;
import Model.Orders;
import Model.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * Business logic layer for handling orders.
 * Manages order insertion, including stock validation and bill generation.
 */

public class OrderBLL {

    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private BillBLL billBLL;
    private ClientBLL clientBLL;
    private ProductBLL productBLL;
    private BillDAO billDAO;
    private BillController billController;

    public OrderBLL() {
        this.orderDAO = new OrderDAO();
        this.productDAO = new ProductDAO();
        this.billBLL = new BillBLL();
        this.clientBLL = new ClientBLL();
        this.productBLL = new ProductBLL();
        this.billDAO = new BillDAO();
    }

    private int getNextBillId() throws SQLException {
        List<Bill> bills = billBLL.findAll();
        return bills.stream().mapToInt(Bill::id).max().orElse(0) + 1;
    }



    /**
     * Inserts a new order after validating stock availability, updates product stock,
     * creates a corresponding bill, and refreshes the bill view.
     *
     * @param order the Orders object to insert
     * @param billController the BillController to refresh the bill view
     */

    public void insert(Orders order, BillController billController) throws SQLException {
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        Product product = productDAO.findById(order.getProductID());
        if (product == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        if (product.getQuantity() < order.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock.");
        }

        product.setQuantity(product.getQuantity() - order.getQuantity());
        productDAO.update(product);
        orderDAO.insert(order);

        Client client = clientBLL.findById(order.getClientID());
        Product produs = productBLL.findById(order.getProductID());

        int totalPrice = produs.getPrice() * order.getQuantity();
        int nextBillId = getNextBillId();


        Bill bill = new Bill(
                nextBillId,
                client.getName(),
                product.getName(),
                order.getQuantity(),
                totalPrice
        );

        billDAO.insert(bill);
        billController=billController;
        billController.refreshBillView();

    }

    public List<Orders> findAll() {
        return orderDAO.findAll();
    }

}
