package seedu.homechef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends HomeChefStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getHomeChefFilePath();

    @Override
    Optional<ReadOnlyHomeChef> readHomeChef() throws DataLoadingException;

    @Override
    void saveHomeChef(ReadOnlyHomeChef homeChef) throws IOException;

}
