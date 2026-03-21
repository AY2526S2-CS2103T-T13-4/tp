package seedu.homechef.model.menu;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a MenuBook.
 */
public interface ReadOnlyMenuBook {

    /**
     * Returns an unmodifiable view of the menu items list.
     * This list will not contain any duplicate menu items.
     */
    ObservableList<MenuItem> getMenuItemList();
}
