package Utils;

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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIssuedDate() { return issuedDate; }
    public void setIssuedDate(String issuedDate) { this.issuedDate = issuedDate; }

    public String getDeadline() { return deadline; }
    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }
}
