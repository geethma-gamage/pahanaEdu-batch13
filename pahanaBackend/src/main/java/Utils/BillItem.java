package Utils;

public class BillItem {
    private int itemId;
    private String itemName;
    private int quantity;
    private double price;

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    // ✅ No-arg constructor required for JSON-B
    public BillItem() {}

    // Convenience constructor
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
=======
<<<<<<< HEAD
    // <-- Make this constructor public and implement it like this:
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    public BillItem(int itemId, int quantity, double price) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
=======
>>>>>>> 2a7c6fe (Home and others codes)
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
>>>>>>> 8d4a862db93c08c7927b522ee21514342ea94507
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
