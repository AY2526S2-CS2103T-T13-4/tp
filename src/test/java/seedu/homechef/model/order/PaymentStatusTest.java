package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PaymentStatusTest {

    @Test
    public void constructor_paidAndUnpaid_setsStatusCorrectly() {
        PaymentStatus paidStatus = PaymentStatus.PAID;
        PaymentStatus unpaidStatus = PaymentStatus.UNPAID;

        assertTrue(paidStatus.isPaid());
        assertFalse(unpaidStatus.isPaid());
    }

    @Test
    public void toString_paidAndUnpaid_returnsCorrectString() {
        PaymentStatus paidStatus = PaymentStatus.PAID;
        PaymentStatus unpaidStatus = PaymentStatus.UNPAID;

        assertEquals("Paid", paidStatus.toString());
        assertEquals("Unpaid", unpaidStatus.toString());
    }

    @Test
    public void equals() {
        PaymentStatus paidStatus1 = PaymentStatus.PAID;
        PaymentStatus paidStatus2 = PaymentStatus.PAID;
        PaymentStatus unpaidStatus = PaymentStatus.UNPAID;

        // same object
        assertTrue(paidStatus1.equals(paidStatus1));

        // same value
        assertTrue(paidStatus1.equals(paidStatus2));

        // different value
        assertFalse(paidStatus1.equals(unpaidStatus));

        // null
        assertFalse(paidStatus1.equals(null));

        // different type
        assertFalse(paidStatus1.equals("Paid"));
    }

    @Test
    public void hashCodeTest() {
        PaymentStatus paidStatus = PaymentStatus.PAID;
        PaymentStatus unpaidStatus = PaymentStatus.UNPAID;

        assertEquals(paidStatus.hashCode(), PaymentStatus.PAID.hashCode());
        assertNotEquals(paidStatus.hashCode(), PaymentStatus.UNPAID.hashCode());
        assertNotEquals(paidStatus.hashCode(), unpaidStatus.hashCode());
    }

}
