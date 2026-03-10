package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PaymentStatusTest {

    @Test
    public void constructor_default_isUnpaid() {
        PaymentStatus status = new PaymentStatus();
        assertFalse(status.status);
    }

}
