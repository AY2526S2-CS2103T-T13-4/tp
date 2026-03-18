package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Customer(null));
    }

    @Test
    public void constructor_invalidCustomer_throwsIllegalArgumentException() {
        String invalidCustomer = "";
        assertThrows(IllegalArgumentException.class, () -> new Customer(invalidCustomer));
    }

    @Test
    public void isValidCustomer() {
        // null Customer
        assertThrows(NullPointerException.class, () -> Customer.isValidCustomer(null));

        // invalid Customer
        assertFalse(Customer.isValidCustomer("")); // empty string
        assertFalse(Customer.isValidCustomer(" ")); // spaces only
        assertFalse(Customer.isValidCustomer("^")); // only non-alphanumeric characters
        assertFalse(Customer.isValidCustomer("peter*")); // contains non-alphanumeric characters

        // valid Customer
        assertTrue(Customer.isValidCustomer("peter jack")); // alphabets only
        assertTrue(Customer.isValidCustomer("12345")); // numbers only
        assertTrue(Customer.isValidCustomer("peter the 2nd")); // alphanumeric characters
        assertTrue(Customer.isValidCustomer("Capital Tan")); // with capital letters
        assertTrue(Customer.isValidCustomer("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Customer customer = new Customer("Valid Customer");

        // same values -> returns true
        assertTrue(customer.equals(new Customer("Valid Customer")));

        // same object -> returns true
        assertTrue(customer.equals(customer));

        // null -> returns false
        assertFalse(customer.equals(null));

        // different types -> returns false
        assertFalse(customer.equals(5.0f));

        // different values -> returns false
        assertFalse(customer.equals(new Customer("Other Valid Customer")));
    }
}
