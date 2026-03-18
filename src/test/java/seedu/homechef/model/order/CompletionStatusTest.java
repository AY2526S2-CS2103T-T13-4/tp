package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CompletionStatusTest {
    @Test
    public void isValidCompletionStatus() {
        // invalid completion status
        assertFalse(CompletionStatus.isValidCompletionStatus(""));
        assertFalse(CompletionStatus.isValidCompletionStatus("Invalid status"));

        // valid completion status
        assertTrue(CompletionStatus.isValidCompletionStatus("In progress"));
        assertTrue(CompletionStatus.isValidCompletionStatus("Completed"));
    }

    @Test
    public void equals() {
        CompletionStatus completionStatus = new CompletionStatus("Completed");

        // same values -> returns true
        assertTrue(completionStatus.equals(new CompletionStatus("Completed")));

        // same object -> returns true
        assertTrue(completionStatus.equals(completionStatus));

        // null -> returns false
        assertFalse(completionStatus.equals(null));

        // different types -> returns false
        assertFalse(completionStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(completionStatus.equals(new CompletionStatus("In progress")));
    }

    @Test
    public void hashCodeTest() {
        CompletionStatus completionStatus1 = new CompletionStatus("In progress");
        CompletionStatus completionStatus2 = new CompletionStatus("Completed");

        assertEquals(completionStatus1.hashCode(), CompletionStatusEnum.IN_PROGRESS.hashCode());
        assertNotEquals(completionStatus1.hashCode(), CompletionStatusEnum.COMPLETED.hashCode());
        assertNotEquals(completionStatus1.hashCode(), completionStatus2.hashCode());
    }

    @Test
    public void toStringTest() {
        CompletionStatus completionStatus1 = new CompletionStatus("In progress");
        CompletionStatus completionStatus2 = new CompletionStatus("Completed");

        assertEquals(completionStatus1.toString(), "In progress");
        assertEquals(completionStatus2.toString(), "Completed");
    }
}
