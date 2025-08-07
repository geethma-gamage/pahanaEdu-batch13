package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TenderDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu_bookshop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Tender> getAllTenders() throws SQLException {
        List<Tender> tenders = new ArrayList<>();
        String sql = "SELECT * FROM tenders";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tender tender = new Tender(
                    rs.getInt("tender_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("issued_date"),
                    rs.getString("deadline"),
                    rs.getString("email"),
                    rs.getDouble("budget")
                );
                tenders.add(tender);
            }
        }
        return tenders;
    }

    public Tender getTender(int tenderId) throws SQLException {
        String sql = "SELECT * FROM tenders WHERE tender_id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tenderId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Tender(
                    rs.getInt("tender_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("issued_date"),
                    rs.getString("deadline"),
                    rs.getString("email"),
                    rs.getDouble("budget")
                );
            }
        }
        return null;
    }

    public boolean addTender(Tender tender) throws SQLException {
        String sql = "INSERT INTO tenders (title, description, issued_date, deadline, email, budget) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tender.getTitle());
            stmt.setString(2, tender.getDescription());
            stmt.setString(3, tender.getIssuedDate());
            stmt.setString(4, tender.getDeadline());
            stmt.setString(5, tender.getEmail());
            stmt.setDouble(6, tender.getBudget());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateTender(Tender tender) throws SQLException {
        String sql = "UPDATE tenders SET title=?, description=?, issued_date=?, deadline=?, email=?, budget=? WHERE tender_id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tender.getTitle());
            stmt.setString(2, tender.getDescription());
            stmt.setString(3, tender.getIssuedDate());
            stmt.setString(4, tender.getDeadline());
            stmt.setString(5, tender.getEmail());
            stmt.setDouble(6, tender.getBudget());
            stmt.setInt(7, tender.getTenderId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteTender(int tenderId) throws SQLException {
        String sql = "DELETE FROM tenders WHERE tender_id=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tenderId);
            return stmt.executeUpdate() > 0;
        }
    }
}
