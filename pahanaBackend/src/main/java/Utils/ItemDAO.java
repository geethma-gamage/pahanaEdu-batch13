package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu_bookshop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Get all items
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                items.add(item);
            }
        }
        return items;
    }

    // Get one item
    public Item getItem(int id) throws SQLException {
        String sql = "SELECT * FROM item WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
            }
        }
        return null;
    }

    // Add new item
    public boolean addItem(Item item) throws SQLException {
        String sql = "INSERT INTO item (name, price, quantity, category) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setString(4, item.getCategory());
            return stmt.executeUpdate() > 0;
        }
    }

    // Update item
    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE item SET name=?, price=?, quantity=?, category=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getQuantity());
            stmt.setString(4, item.getCategory());
            stmt.setInt(5, item.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Delete item
    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM item WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
