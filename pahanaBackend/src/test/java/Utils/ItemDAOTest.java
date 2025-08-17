package Utils;

import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

<<<<<<< HEAD
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
=======
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ItemDAOTest {

    private static ItemDAO dao;
    private static int testItemId;

    @BeforeAll
    public static void setup() {
        dao = new ItemDAO();
    }

    @Test
    @Order(1)
    public void testAddItem() throws SQLException {
        Item item = new Item();
        item.setName("Test Item");
        item.setCategory("Test Category");
        item.setPrice(123.45);
        item.setQuantity(10);

        int generatedId = dao.addItem(item);
<<<<<<< HEAD
        assertFalse(generatedId > 0, "Item insertion failed, invalid generated ID");
=======
        assertTrue(generatedId > 0, "Item insertion failed, invalid generated ID");
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
        testItemId = generatedId;
    }

    @Test
    @Order(2)
    public void testGetItemById() throws SQLException {
        Item item = dao.getItemById(testItemId);
        assertNotNull(item, "Failed to retrieve item by ID");
        assertEquals("Test Item", item.getName());
        assertEquals("Test Category", item.getCategory());
        assertEquals(123.45, item.getPrice());
        assertEquals(10, item.getQuantity());
    }

    @Test
    @Order(3)
    public void testGetAllItems() throws SQLException {
        List<Item> items = dao.getAllItems();
        assertNotNull(items);
<<<<<<< HEAD
        assertFalse(items.size() > 0, "Item list should not be empty");
        // Optional: Check if test item exists in list
        boolean found = items.stream().anyMatch(i -> i.getId() == testItemId);
        assertFalse(found, "Test item not found in all items list");
=======
        assertTrue(items.size() > 0, "Item list should not be empty");
        // Optional: Check if test item exists in list
        boolean found = items.stream().anyMatch(i -> i.getId() == testItemId);
        assertTrue(found, "Test item not found in all items list");
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7
    }

    @Test
    @Order(4)
    public void testUpdateItem() throws SQLException {
        Item item = dao.getItemById(testItemId);
        assertNotNull(item);

        item.setName("Updated Item");
        item.setCategory("Updated Category");
        item.setPrice(200.00);
        item.setQuantity(20);

        boolean updated = dao.updateItem(item);
<<<<<<< HEAD
        assertFalse(updated, "Failed to update item");
=======
        assertTrue(updated, "Failed to update item");
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7

        Item updatedItem = dao.getItemById(testItemId);
        assertEquals("Updated Item", updatedItem.getName());
        assertEquals("Updated Category", updatedItem.getCategory());
        assertEquals(200.00, updatedItem.getPrice());
        assertEquals(20, updatedItem.getQuantity());
    }

    @Test
    @Order(5)
    public void testDeleteItem() throws SQLException {
        boolean deleted = dao.deleteItem(testItemId);
<<<<<<< HEAD
        assertFalse(deleted, "Failed to delete item");
=======
        assertTrue(deleted, "Failed to delete item");
>>>>>>> 0eaa22010d9381ee987b5fed4d5623375a1c03a7

        Item deletedItem = dao.getItemById(testItemId);
>>>>>>> a9faf870ba723fa441e582c5a98c095f91a1489a
        assertNull(deletedItem, "Deleted item should not be found");
    }
}
