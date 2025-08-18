package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu_bookshop";
    private static final String USER = "root";
<<<<<<< HEAD
    private static final String PASSWORD = ""; // change if needed
=======
<<<<<<< HEAD
    private static final String PASSWORD = "";
=======
    private static final String PASSWORD = ""; // change if needed
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
    // Get all items
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
<<<<<<< HEAD
=======
<<<<<<< HEAD
                Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
                items.add(item);
            }
        }
        return items;
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
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
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT * FROM item WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Item item = new Item();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setCategory(rs.getString("category"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                return item;
<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
            }
        }
        return null;
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
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
=======
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    public int addItem(Item item) throws SQLException {
        String sql = "INSERT INTO item (name, category, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQuantity());

            int affected = stmt.executeUpdate();
            if (affected == 0) return -1;

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else {
                return -1;
            }
        }
    }

    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE item SET name=?, category=?, price=?, quantity=? WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getCategory());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQuantity());
            stmt.setInt(5, item.getId());

<<<<<<< HEAD
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
            return stmt.executeUpdate() > 0;
        }
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
    // Delete item
=======
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM item WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
<<<<<<< HEAD

=======
<<<<<<< HEAD
=======

>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
