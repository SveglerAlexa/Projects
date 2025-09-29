package DataAccess;

import Model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import Connection.ConnectionFactory;

import static DataAccess.AbstractDAO.LOGGER;

public class BillDAO {

    public List<Bill> findAll() throws SQLException {
        List<Bill> bills = new ArrayList<>();

        String query = "SELECT * FROM Bill";
        Connection connection = null;        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bill bill = new Bill(
                        resultSet.getInt("id"),
                        resultSet.getString("clientName"),
                        resultSet.getString("productName"),
                        resultSet.getInt("quantity"),
                        resultSet.getInt("totalPrice")
                );
                bills.add(bill);

            }
            bills = bills.stream()
                    .sorted((b1, b2) -> Integer.compare(b1.id(), b2.id()))
                    .collect(Collectors.toList());

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }

        return bills;

    }

    public void insert(Bill bill) {
        String query = "INSERT INTO Bill (id,clientName, productName, quantity, totalPrice) VALUES (?, ?, ?, ?,?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1,bill.id());
            statement.setString(2, bill.clientName());
            statement.setString(3, bill.productName());
            statement.setInt(4, bill.quantity());
            statement.setInt(5, bill.totalPrice());

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:insert " + e.getMessage());
            e.printStackTrace();
        }
    }




}
