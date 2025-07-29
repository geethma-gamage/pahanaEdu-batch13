/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.sql.*;
import java.util.List;
/**
 *
 * @author rosha
 */
public class BillDAO {
    private final String URL = "jdbc:mysql://localhost:3306/pahana_edu_bookshop";
    private final String USER = "root";
    private final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public int saveBill(Bill bill) throws SQLException {
        String insertBillSQL = "INSERT INTO bills (account_number, total_amount) VALUES (?, ?)";
        String insertItemSQL = "INSERT INTO bill_items (bill_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement billStmt = conn.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS)) {
                billStmt.setInt(1, bill.getAccountNumber());
                billStmt.setDouble(2, bill.getTotalAmount());
                billStmt.executeUpdate();

                ResultSet rs = billStmt.getGeneratedKeys();
                if (rs.next()) {
                    int billId = rs.getInt(1);

                    try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL)) {
                        for (BillItem item : bill.getItems()) {
                            itemStmt.setInt(1, billId);
                            itemStmt.setInt(2, item.getItemId());
                            itemStmt.setInt(3, item.getQuantity());
                            itemStmt.setDouble(4, item.getPrice());
                            itemStmt.addBatch();
                        }
                        itemStmt.executeBatch();
                    }

                    conn.commit();
                    return billId;
                } else {
                    conn.rollback();
                    throw new SQLException("Failed to generate bill ID.");
                }
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
