package Utils;

import java.sql.Timestamp;
import java.util.List;

public class Bill {
    private int billId;
    private int accountNumber;
    private String customerName;
    private double totalAmount;
    private Timestamp createdAt;
<<<<<<< HEAD
    private List<BillItem> items;

    public Bill() {} // ✅ Needed for JSON-B
=======
<<<<<<< HEAD
    private List<BillItem> items;

    public Bill() {} // ✅ Needed for JSON-B
=======

    private List<BillItem> items; // Optional for saveBill()

    // Getters and Setters for all fields
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<BillItem> getItems() {
        return items;
    }

    public void setItems(List<BillItem> items) {
        this.items = items;
    }
}
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======

>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
