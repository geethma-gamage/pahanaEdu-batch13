package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TenderDAO {

    private final String DB_URL = "jdbc:mysql://localhost:3306/pahana_edu_bookshop";  // change DB name if needed
    private final String USER = "root";  // your DB user
    private final String PASS = "";  // your DB password

    // Add Tender, returns generated id
    public int addTender(Tender tender) throws SQLException {
        String sql = "INSERT INTO tenders (title, description, deadline, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tender.getTitle());
            ps.setString(2, tender.getDescription());
            ps.setDate(3, tender.getDeadline());
            ps.setDouble(4, tender.getAmount());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating tender failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tender.setId(generatedKeys.getInt(1));
                    return tender.getId();
                } else {
                    throw new SQLException("Creating tender failed, no ID obtained.");
                }
            }
        }
    }

    // Get Tender by id
    public Tender getTenderById(int id) throws SQLException {
        String sql = "SELECT * FROM tenders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tender tender = new Tender();
                    tender.setId(rs.getInt("id"));
                    tender.setTitle(rs.getString("title"));
                    tender.setDescription(rs.getString("description"));
                    tender.setDeadline(rs.getDate("deadline"));
                    tender.setAmount(rs.getDouble("amount"));
                    return tender;
                }
            }
        }
        return null;
    }

    // Get all Tenders
    public List<Tender> getAllTenders() throws SQLException {
        List<Tender> tenders = new ArrayList<>();
        String sql = "SELECT * FROM tenders";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tender tender = new Tender();
                tender.setId(rs.getInt("id"));
                tender.setTitle(rs.getString("title"));
                tender.setDescription(rs.getString("description"));
                tender.setDeadline(rs.getDate("deadline"));
                tender.setAmount(rs.getDouble("amount"));
                tenders.add(tender);
            }
        }
        return tenders;
    }

    // Update Tender
    public boolean updateTender(Tender tender) throws SQLException {
        String sql = "UPDATE tenders SET title = ?, description = ?, deadline = ?, amount = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tender.getTitle());
            ps.setString(2, tender.getDescription());
            ps.setDate(3, tender.getDeadline());
            ps.setDouble(4, tender.getAmount());
            ps.setInt(5, tender.getId());

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Delete Tender
    public boolean deleteTender(int id) throws SQLException {
        String sql = "DELETE FROM tenders WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }
}
