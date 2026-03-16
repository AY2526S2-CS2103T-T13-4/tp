package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

    @Test
    public void getDate_returnsConstructorDate() {
        LocalDate date = LocalDate.of(2026, 3, 26);
        assertEquals(date, new DateIsOnDatePredicate(date).getDate());
    }

    @Test
    public void equals() {
        LocalDate date = LocalDate.of(2026, 3, 26);
        LocalDate otherDate = LocalDate.of(2026, 3, 27);

        DateIsOnDatePredicate predicate = new DateIsOnDatePredicate(date);
        DateIsOnDatePredicate samePredicate = new DateIsOnDatePredicate(date);
        DateIsOnDatePredicate differentPredicate = new DateIsOnDatePredicate(otherDate);

        assertEquals(predicate, predicate);
        assertEquals(predicate, samePredicate);

        assertNotEquals(predicate, null);
        assertNotEquals(predicate, "not a predicate");
        assertNotEquals(predicate, differentPredicate);
    }
}
