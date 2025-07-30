package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DatabaseUtil;

public class CustomerDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu_bookshop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer(
                    rs.getString("account_number"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("telephone"),
                    rs.getString("email")
                );
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer getCustomer(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM customers WHERE account_number=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getString("account_number"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("telephone"),
                    rs.getString("email")
                );
            }
        }
        return null;
    }

    public boolean addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (account_number, name, address, telephone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getTelephone());
            stmt.setString(5, customer.getEmail());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET name=?, address=?, telephone=?, email=? WHERE account_number=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getTelephone());
            stmt.setString(4, customer.getEmail());
            stmt.setString(5, customer.getAccountNumber());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteCustomer(String accountNumber) throws SQLException {
        String sql = "DELETE FROM customers WHERE account_number=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            return stmt.executeUpdate() > 0;
        }
    }
}
