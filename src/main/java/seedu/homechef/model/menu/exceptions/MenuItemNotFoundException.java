package seedu.homechef.model.menu.exceptions;

/**
 * Signals that the operation is unable to find the specified MenuItem.
 */
public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException() {
        super("Menu item not found");
    }
}
