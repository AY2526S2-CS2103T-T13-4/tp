package seedu.homechef.model.menu;

import static java.util.Objects.requireNonNull;
import static seedu.homechef.commons.util.AppUtil.checkArgument;

/**
 * Represents a MenuItem's name in the menu.
 * Guarantees: immutable; is valid as declared in {@link #isValidMenuItemName(String)}
 */
public class MenuItemName {

    public static final String MESSAGE_CONSTRAINTS =
            "Menu item names should only contain alphanumeric characters, spaces, "
            + "parentheses, brackets, and hyphens, and should not be blank";

    // Uses the same regex as Food.VALIDATION_REGEX so food names on orders always match menu items.
    // Note: the spec quotes a simpler regex — this extended form is the actual Food regex in the codebase.
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ()\\[\\]-]*";

    public final String fullName;

    /**
     * Constructs a {@code MenuItemName}.
     *
     * @param name A valid menu item name.
     */
    public MenuItemName(String name) {
        requireNonNull(name);
        checkArgument(isValidMenuItemName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid menu item name.
     */
    public static boolean isValidMenuItemName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MenuItemName)) {
            return false;
        }
        MenuItemName otherName = (MenuItemName) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
