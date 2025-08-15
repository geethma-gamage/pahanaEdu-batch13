package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private final String URL = "jdbc:mysql://localhost:3306/pahana_edu_bookshop";
    private final String USER = "root";
    private final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ✅ Save bill and its items
    public int saveBill(Bill bill) throws SQLException {
        String insertBillSQL = "INSERT INTO bills (account_number, customer_name, total_amount) VALUES (?, ?, ?)";
        String insertItemSQL = "INSERT INTO bill_items (bill_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement billStmt = conn.prepareStatement(insertBillSQL, Statement.RETURN_GENERATED_KEYS)) {
                billStmt.setInt(1, bill.getAccountNumber());
                billStmt.setString(2, bill.getCustomerName());
                billStmt.setDouble(3, bill.getTotalAmount());
                billStmt.executeUpdate();

                ResultSet rs = billStmt.getGeneratedKeys();
                if (rs.next()) {
                    int billId = rs.getInt(1);

                    try (PreparedStatement itemStmt = conn.prepareStatement(insertItemSQL)) {
                        if (bill.getItems() != null) {
                            for (BillItem item : bill.getItems()) {
                                itemStmt.setInt(1, billId);
                                itemStmt.setInt(2, item.getItemId());
                                itemStmt.setInt(3, item.getQuantity());
                                itemStmt.setDouble(4, item.getPrice());
                                itemStmt.addBatch();
                            }
                            itemStmt.executeBatch();
                        }
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

    // ✅ Get all bills
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills ORDER BY created_at DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setBillId(rs.getInt("bill_id"));
                bill.setAccountNumber(rs.getInt("account_number"));
                bill.setCustomerName(rs.getString("customer_name"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setCreatedAt(rs.getTimestamp("created_at"));
                bills.add(bill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bills;
    }
}
