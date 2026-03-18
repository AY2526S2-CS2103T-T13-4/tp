package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.homechef.testutil.OrderBuilder;

public class CustomerContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CustomerContainsKeywordsPredicate firstPredicate = new CustomerContainsKeywordsPredicate(
                firstPredicateKeywordList);
        CustomerContainsKeywordsPredicate secondPredicate = new CustomerContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CustomerContainsKeywordsPredicate firstPredicateCopy = new CustomerContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different order -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_customerContainsKeywords_returnsTrue() {
        // One keyword
        CustomerContainsKeywordsPredicate predicate = new CustomerContainsKeywordsPredicate(
                Collections.singletonList("Alice"));
        assertTrue(predicate.test(new OrderBuilder().withCustomer("Alice Bob").build()));

        // Multiple keywords
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new OrderBuilder().withCustomer("Alice Bob").build()));

        // Only one matching keyword
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new OrderBuilder().withCustomer("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new OrderBuilder().withCustomer("Alice Bob").build()));
    }

    @Test
    public void test_customerDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CustomerContainsKeywordsPredicate predicate = new CustomerContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OrderBuilder().withCustomer("Alice").build()));

        // Non-matching keyword
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new OrderBuilder().withCustomer("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new CustomerContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new OrderBuilder().withCustomer("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        CustomerContainsKeywordsPredicate predicate = new CustomerContainsKeywordsPredicate(keywords);

        String expected = CustomerContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
