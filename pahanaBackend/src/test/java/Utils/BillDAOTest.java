package Utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BillDAOTest {

    private BillDAO billDAO;

    @BeforeEach
    public void setUp() {
        billDAO = new BillDAO();
    }

    @Test
    public void testSaveBillAndRetrieve() throws Exception {
        // Create BillItems
        List<BillItem> items = new ArrayList<>();
        items.add(new BillItem(1, 2, 100.0));  // itemId=1, quantity=2, price=100.0
        items.add(new BillItem(2, 1, 50.0));   // itemId=2, quantity=1, price=50.0

        // Create Bill
        Bill bill = new Bill();
        bill.setAccountNumber(123456);
<<<<<<< HEAD
        bill.setCustomerName("Testing 01");
=======
<<<<<<< HEAD
        bill.setCustomerName("Testing 01");
=======
        bill.setCustomerName("JUnit Test Customer");
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
        bill.setItems(items);
        bill.setTotalAmount(250.0);

        // Save bill and get generated bill ID
        int generatedBillId = billDAO.saveBill(bill);
        assertTrue(generatedBillId > 0, "Generated bill ID should be positive");

        // Retrieve all bills and check if the inserted bill exists
        List<Bill> bills = billDAO.getAllBills();

        boolean found = bills.stream().anyMatch(b ->
            b.getBillId() == generatedBillId &&
            b.getAccountNumber() == bill.getAccountNumber() &&
            b.getCustomerName().equals(bill.getCustomerName()) &&
            b.getTotalAmount() == bill.getTotalAmount()
        );

        assertTrue(found, "Saved bill should be found in the list of all bills");
    }

    @Test
    public void testGetAllBillsReturnsList() {
        List<Bill> bills = billDAO.getAllBills();
        assertNotNull(bills);
        assertTrue(bills.size() >= 0);
    }
}
