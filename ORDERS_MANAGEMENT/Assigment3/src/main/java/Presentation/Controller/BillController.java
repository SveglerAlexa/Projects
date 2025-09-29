package Presentation.Controller;

import BusinessLogic.BillBLL;
import Presentation.View.BillView;

import java.sql.SQLException;

/**
 * The BillController class manages the interaction between the BillView and the business logic layer (BillBLL).
 * It is responsible for initializing and refreshing the data displayed in the bill table.
 */

public class BillController {

    BillView billView;
    private BillBLL billBLL;

    /**
     * Constructs a BillController with the specified BillView.
     * Initializes the bill table with all existing bill records from the database.
     *
     * @param billView the view component responsible for displaying bill data
     */

    public BillController(BillView billView) throws SQLException {
        this.billView = billView;
        this.billBLL = new BillBLL();

        billView.setData(billBLL.findAll());
    }

    /**
     * Refreshes the bill table in the view by retrieving the latest bill data from the database.
     */

    public void refreshBillView() throws SQLException {
        billView.setData(billBLL.findAll());
    }


}
