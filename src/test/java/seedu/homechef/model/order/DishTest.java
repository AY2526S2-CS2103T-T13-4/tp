package seedu.homechef.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.homechef.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DishTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Dish(null));
    }

    @Test
    public void constructor_invalidDish_throwsIllegalArgumentException() {
        String invalidDish = "";
        assertThrows(IllegalArgumentException.class, () -> new Dish(invalidDish));
    }

    @Test
    public void isValidDish() {
        // null name
        assertThrows(NullPointerException.class, () -> Dish.isValidDish(null));

        // invalid name
        assertFalse(Dish.isValidDish("")); // empty string
        assertFalse(Dish.isValidDish(" ")); // spaces only
        assertFalse(Dish.isValidDish("^")); // only non-alphanumeric characters
        assertFalse(Dish.isValidDish("cookie*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Dish.isValidDish("cookies")); // alphabets only
        assertTrue(Dish.isValidDish("12345")); // numbers only
        assertTrue(Dish.isValidDish("5 cookies")); // alphanumeric characters
        assertTrue(Dish.isValidDish("Cookies")); // with capital letters
        assertTrue(Dish.isValidDish("Cookies from Heaven")); // long names
        assertTrue(Dish.isValidDish("Cookies (7pcs) - [Blueberry]")); // (), [] and - are allowed
    }

    @Test
    public void equals() {
        Dish name = new Dish("Valid Dish");

        // same values -> returns true
        assertTrue(name.equals(new Dish("Valid Dish")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Dish("Other Valid Dish")));
    }
}
