package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CompletionStatusTest {
    @Test
    public void isValidCompletionStatus() {
        // invalid completion status
        assertFalse(CompletionStatus.isValidCompletionStatus(""));
        assertFalse(CompletionStatus.isValidCompletionStatus("Invalid status"));

        // valid completion status
        assertTrue(CompletionStatus.isValidCompletionStatus("Pending"));
        assertTrue(CompletionStatus.isValidCompletionStatus("In progress"));
        assertTrue(CompletionStatus.isValidCompletionStatus("Completed"));
    }

    @Test
    public void equals() {
        CompletionStatus completionStatus = CompletionStatus.COMPLETED;

        // same values -> returns true
        assertTrue(completionStatus.equals(CompletionStatus.COMPLETED));

        // same object -> returns true
        assertEquals(completionStatus, completionStatus);

        // null -> returns false
        assertNotEquals(null, completionStatus);

        // different types -> returns false
        assertNotEquals(5.0f, completionStatus);

        // different values -> returns false
        assertFalse(completionStatus.equals(CompletionStatus.IN_PROGRESS));
    }

    @Test
    public void hashCodeTest() {
        CompletionStatus completionStatus1 = CompletionStatus.IN_PROGRESS;
        CompletionStatus completionStatus2 = CompletionStatus.COMPLETED;

        assertEquals(completionStatus1.hashCode(), CompletionStatus.IN_PROGRESS.hashCode());
        assertNotEquals(completionStatus1.hashCode(), CompletionStatus.COMPLETED.hashCode());
        assertNotEquals(completionStatus1.hashCode(), completionStatus2.hashCode());
    }

    @Test
    public void toStringTest() {
        CompletionStatus completionStatus1 = CompletionStatus.IN_PROGRESS;
        CompletionStatus completionStatus2 = CompletionStatus.COMPLETED;
        CompletionStatus completionStatus3 = CompletionStatus.PENDING;

        assertEquals("In Progress", completionStatus1.toString());
        assertEquals("Completed", completionStatus2.toString());
        assertEquals("Pending", completionStatus3.toString());
    }

    @Test
    public void fromString_validString() {
        CompletionStatus completionStatus1 = CompletionStatus.IN_PROGRESS;
        CompletionStatus completionStatus2 = CompletionStatus.COMPLETED;
        CompletionStatus completionStatus3 = CompletionStatus.PENDING;

        assertEquals(completionStatus1, CompletionStatus.fromString("In progress"));
        assertEquals(completionStatus2, CompletionStatus.fromString("Completed"));
        assertEquals(completionStatus3, CompletionStatus.fromString("Pending"));
    }

    @Test
    public void fromString_invalidString_throwsIllegalArgumentException() {
        String invalidString = "";
        assertThrows(IllegalArgumentException.class, () -> CompletionStatus.fromString(invalidString));
    }

    @Test
    public void fromString_nullString_throwsNullPointerException() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> CompletionStatus.fromString(nullString));
    }
}
