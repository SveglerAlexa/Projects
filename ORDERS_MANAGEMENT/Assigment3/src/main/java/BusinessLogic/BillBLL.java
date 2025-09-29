package BusinessLogic;

import DataAccess.BillDAO;
import Model.Bill;

import java.sql.SQLException;
import java.util.List;

/**
 * Business logic layer for managing bills.
 * Handles operations such as retrieving all bills and inserting a new bill.
 */

public class BillBLL {

    private BillDAO billDAO;

    public BillBLL() {
        this.billDAO = new BillDAO();
    }

    public List<Bill> findAll() throws SQLException {
        return billDAO.findAll();
    }

    public void insertBill(Bill bill) {
        billDAO.insert(bill);
    }

}
