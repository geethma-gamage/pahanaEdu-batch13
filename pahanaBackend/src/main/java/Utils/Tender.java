package Utils;

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
import java.sql.Date;

public class Tender {
    private int id;
    private String title;
    private String description;
    private Date deadline;
    private double amount;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
=======
public class Tender {
    private int tenderId;
    private String title;
    private String description;
    private String issuedDate;
    private String deadline;
    private String email;
    private double budget;

    public Tender(int tenderId, String title, String description, String issuedDate, String deadline, String email, double budget) {
        this.tenderId = tenderId;
        this.title = title;
        this.description = description;
        this.issuedDate = issuedDate;
        this.deadline = deadline;
        this.email = email;
        this.budget = budget;
    }

    // Getters and setters
    public int getTenderId() { return tenderId; }
    public void setTenderId(int tenderId) { this.tenderId = tenderId; }
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public void setTenderId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
=======
    public String getIssuedDate() { return issuedDate; }
    public void setIssuedDate(String issuedDate) { this.issuedDate = issuedDate; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
}
