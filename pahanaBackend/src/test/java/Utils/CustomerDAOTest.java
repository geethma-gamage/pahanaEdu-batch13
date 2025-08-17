package Utils;

import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerDAOTest {

    private static CustomerDAO dao;
    private static final String TEST_ACCOUNT_NUMBER = "999999"; // Integer as String

    @BeforeAll
    public static void setup() {
        dao = new CustomerDAO();
    }

    @Test
    @Order(1)
    public void testAddCustomer() throws SQLException {
        Customer customer = new Customer();
        customer.setAccountNumber(TEST_ACCOUNT_NUMBER);
        customer.setName("Test User");
        customer.setAddress("123 Test Street");
        customer.setTelephone("0771234567");
        customer.setEmail("test@example.com");

        boolean result = dao.addCustomer(customer);
        assertTrue(result, "Failed to add customer");
    }

    @Test
    @Order(2)
    public void testGetCustomer() throws SQLException {
        Customer customer = dao.getCustomer(TEST_ACCOUNT_NUMBER);
        assertNotNull(customer, "Customer not found");
        assertEquals("Test User", customer.getName());
        assertEquals("123 Test Street", customer.getAddress());
        assertEquals("0771234567", customer.getTelephone());
        assertEquals("test@example.com", customer.getEmail());
    }

    @Test
    @Order(3)
    public void testGetAllCustomers() throws SQLException {
        List<Customer> customers = dao.getAllCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() > 0, "Customer list should not be empty");

        boolean found = customers.stream()
            .anyMatch(c -> TEST_ACCOUNT_NUMBER.equals(c.getAccountNumber()));
        assertTrue(found, "Test customer not found in customer list");
    }

    @Test
    @Order(4)
    public void testUpdateCustomer() throws SQLException {
        Customer customer = dao.getCustomer(TEST_ACCOUNT_NUMBER);
        assertNotNull(customer);

        customer.setName("Updated User");
        customer.setAddress("456 Updated Street");
        customer.setTelephone("0787654321");
        customer.setEmail("updated@example.com");

        boolean updated = dao.updateCustomer(customer);
        assertTrue(updated, "Customer update failed");

        Customer updatedCustomer = dao.getCustomer(TEST_ACCOUNT_NUMBER);
        assertEquals("Updated User", updatedCustomer.getName());
        assertEquals("456 Updated Street", updatedCustomer.getAddress());
        assertEquals("0787654321", updatedCustomer.getTelephone());
        assertEquals("updated@example.com", updatedCustomer.getEmail());
    }

    @Test
    @Order(5)
    public void testDeleteCustomer() throws SQLException {
        boolean deleted = dao.deleteCustomer(TEST_ACCOUNT_NUMBER);
        assertTrue(deleted, "Customer deletion failed");

        Customer deletedCustomer = dao.getCustomer(TEST_ACCOUNT_NUMBER);
        assertNull(deletedCustomer, "Customer should be deleted but still found");
    }
}
