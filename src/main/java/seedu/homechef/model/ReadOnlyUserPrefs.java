package seedu.homechef.model;

import java.nio.file.Path;

import seedu.homechef.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getHomeChefFilePath();

}
