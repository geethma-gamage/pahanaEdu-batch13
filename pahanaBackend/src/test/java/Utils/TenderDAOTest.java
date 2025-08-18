package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TenderDAOTest {

    private static final String DB_URL = "jdbc:mysql://localhost/batch13";
    private static final String USER = "root";
    private static final String PASS = "bimsara123";

    // Add a new Tender and return generated ID
    public int addTender(Tender tender) throws SQLException {
        String sql = "INSERT INTO tender (title, description, deadline, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tender.getTitle());
            pstmt.setString(2, tender.getDescription());
            pstmt.setDate(3, tender.getDeadline());
            pstmt.setDouble(4, tender.getAmount());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating tender failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating tender failed, no ID obtained.");
                }
            }
        }
    }

    // Get Tender by ID
    public Tender getTenderById(int id) throws SQLException {
        String sql = "SELECT * FROM tender WHERE id = ?";
        Tender tender = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    tender = new Tender();
                    tender.setId(rs.getInt("id"));
                    tender.setTitle(rs.getString("title"));
                    tender.setDescription(rs.getString("description"));
                    tender.setDeadline(rs.getDate("deadline"));
                    tender.setAmount(rs.getDouble("amount"));
                }
            }
        }
        return tender;
    }

    // Get all Tenders
    public List<Tender> getAllTenders() throws SQLException {
        List<Tender> tenders = new ArrayList<>();
        String sql = "SELECT * FROM tender";
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

    // Update existing Tender
    public boolean updateTender(Tender tender) throws SQLException {
        String sql = "UPDATE tender SET title = ?, description = ?, deadline = ?, amount = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tender.getTitle());
            pstmt.setString(2, tender.getDescription());
            pstmt.setDate(3, tender.getDeadline());
            pstmt.setDouble(4, tender.getAmount());
            pstmt.setInt(5, tender.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Delete Tender by ID
    public boolean deleteTender(int id) throws SQLException {
        String sql = "DELETE FROM tender WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }
}
