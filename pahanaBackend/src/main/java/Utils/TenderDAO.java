package Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TenderDAO {

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
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
<<<<<<< HEAD
=======
=======
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
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
                tenders.add(tender);
            }
        }
        return tenders;
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
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
<<<<<<< HEAD
=======
=======
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
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
        }
    }
}
