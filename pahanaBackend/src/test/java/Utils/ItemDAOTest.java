package Utils;

import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemDAOTest {

    private static ItemDAO itemDAO;

    @BeforeAll
    static void setupAll() {
        itemDAO = new ItemDAO();
    }

    @Test
    void testAddAndGetItem() throws SQLException {
        Item newItem = new Item(0, "JUnit Test Book", 1200.50, 10, "Books");
        boolean isAdded = itemDAO.addItem(newItem);
        assertTrue(isAdded, "Item should be added successfully");

        // Get all items and check if inserted one exists
        List<Item> items = itemDAO.getAllItems();
        assertTrue(items.stream().anyMatch(i -> i.getName().equals("JUnit Test Book")),
                "Inserted item should exist in DB");
    }

    @Test
    void testUpdateItem() throws SQLException {
        // Insert an item first
        Item newItem = new Item(0, "Old Name", 500.0, 5, "Stationery");
        itemDAO.addItem(newItem);

        // Fetch the last inserted item
        List<Item> items = itemDAO.getAllItems();
        Item inserted = items.get(items.size() - 1);

        // Update values
        inserted.setName("Updated Name");
        inserted.setPrice(999.99);
        inserted.setQuantity(20);
        inserted.setCategory("Updated Category");

        boolean updated = itemDAO.updateItem(inserted);
        assertTrue(updated, "Item should be updated successfully");

        Item updatedItem = itemDAO.getItem(inserted.getId());
        assertEquals("Updated Name", updatedItem.getName());
        assertEquals(999.99, updatedItem.getPrice());
        assertEquals(20, updatedItem.getQuantity());
        assertEquals("Updated Category", updatedItem.getCategory());
    }

    @Test
    void testDeleteItem() throws SQLException {
        // Insert an item to delete
        Item newItem = new Item(0, "Delete Me", 300.0, 3, "Test");
        itemDAO.addItem(newItem);

        // Fetch the last inserted item
        List<Item> items = itemDAO.getAllItems();
        Item toDelete = items.get(items.size() - 1);

        boolean deleted = itemDAO.deleteItem(toDelete.getId());
        assertTrue(deleted, "Item should be deleted successfully");

        Item deletedItem = itemDAO.getItem(toDelete.getId());
        assertNull(deletedItem, "Deleted item should not be found");
    }
}
