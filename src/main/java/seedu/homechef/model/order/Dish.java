package seedu.homechef.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents an Order's dish name in HomeChef.
 * Guarantees: immutable; is valid as declared in {@link #isValidDish(String)}
 */
public class Dish {

    public static final String MESSAGE_CONSTRAINTS =
            "Dishes should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the dish name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ()\\[\\]-]*";

    public final String dishName;

    /**
     * Constructs a {@code Dish}.
     *
     * @param name A valid dish name.
     */
    public Dish(String name) {
        requireNonNull(name);
        checkArgument(isValidDish(name), MESSAGE_CONSTRAINTS);
        dishName = name;
    }

    /**
     * Returns true if a given string is a valid dish name.
     */
    public static boolean isValidDish(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return dishName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Dish)) {
            return false;
        }

        Dish otherDish = (Dish) other;
        return dishName.equals(otherDish.dishName);
    }

    @Override
    public int hashCode() {
        return dishName.hashCode();
    }

}
