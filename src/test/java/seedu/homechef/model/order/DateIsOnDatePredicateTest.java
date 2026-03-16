package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.TypicalOrders.ALICE;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@code DateIsOnDatePredicate}.
 */
public class DateIsOnDatePredicateTest {

    @Test
    public void test_dateMatches_returnsTrue() {
        LocalDate date = LocalDate.of(2026, 3, 26);
        assertTrue(new DateIsOnDatePredicate(date).test(ALICE));
    }

    @Test
    public void test_dateDoesNotMatch_returnsFalse() {
        LocalDate date = LocalDate.of(2026, 3, 27);
        assertFalse(new DateIsOnDatePredicate(date).test(ALICE));
    }
}
