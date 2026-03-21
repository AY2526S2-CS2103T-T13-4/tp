package seedu.homechef.model.menu.exceptions;

/**
 * Signals that the operation will result in duplicate MenuItems
 * (MenuItems are considered duplicates if they have the same identity).
 */
public class DuplicateMenuItemException extends RuntimeException {
    public DuplicateMenuItemException() {
        super("Operation would result in duplicate menu items");
    }
}
