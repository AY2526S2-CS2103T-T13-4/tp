package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("2026-12-01")); // wrong format
        assertFalse(Date.isValidDate("1-12-2026")); // wrong format
        assertFalse(Date.isValidDate("32-01-2026")); // invalid day
        assertFalse(Date.isValidDate("01-13-2026")); // invalid month
        assertFalse(Date.isValidDate("abc")); // non-numeric input

        // valid date
        assertTrue(Date.isValidDate("01-01-2026"));
        assertTrue(Date.isValidDate("09-12-2026"));
        assertTrue(Date.isValidDate("29-02-2024")); // leap year
        assertTrue(Date.isValidDate("31-12-2030"));
    }

    @Test
    public void equals() {
        Date date = new Date("01-01-2025");

        // same values -> returns true
        assertTrue(date.equals(new Date("01-01-2025")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("02-01-2026")));
    }
}
