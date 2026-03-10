package seedu.homechef.model.order;

import org.junit.jupiter.api.Test;
import seedu.homechef.model.order.completionstatus.CompletionStatus;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

public class CompletionStatusTest {
    @Test
    public void constructor_invalidCompletionStatus_throwsIllegalArgumentException() {
        int invalidCompletionStatus = -1;
        assertThrows(IllegalArgumentException.class, () -> new CompletionStatus(invalidCompletionStatus));
    }

    @Test
    public void isValidCompletionStatus() {
        // invalid completion status
        assertFalse(CompletionStatus.isValidCompletionStatus(-1)); // negative integers
        assertFalse(CompletionStatus.isValidCompletionStatus(Integer.MAX_VALUE)); // excessively large integer

        // valid completion status
        assertTrue(CompletionStatus.isValidCompletionStatus(0));
        assertTrue(CompletionStatus.isValidCompletionStatus(1)); // one character
    }

    @Test
    public void equals() {
        CompletionStatus completionStatus = new CompletionStatus(1);

        // same values -> returns true
        assertTrue(completionStatus.equals(new CompletionStatus(1)));

        // same object -> returns true
        assertTrue(completionStatus.equals(completionStatus));

        // null -> returns false
        assertFalse(completionStatus.equals(null));

        // different types -> returns false
        assertFalse(completionStatus.equals(5.0f));

        // different values -> returns false
        assertFalse(completionStatus.equals(new CompletionStatus(0)));
    }
}
