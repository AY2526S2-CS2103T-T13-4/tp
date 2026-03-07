package seedu.homechef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.ReadOnlyHomeChef;
import seedu.homechef.model.ReadOnlyUserPrefs;
import seedu.homechef.model.UserPrefs;

/**
 * Manages storage of HomeChef data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private HomeChefStorage homeChefStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code HomeChefStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(HomeChefStorage homeChefStorage, UserPrefsStorage userPrefsStorage) {
        this.homeChefStorage = homeChefStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ HomeChef methods ==============================

    @Override
    public Path getHomeChefFilePath() {
        return homeChefStorage.getHomeChefFilePath();
    }

    @Override
    public Optional<ReadOnlyHomeChef> readHomeChef() throws DataLoadingException {
        return readHomeChef(homeChefStorage.getHomeChefFilePath());
    }

    @Override
    public Optional<ReadOnlyHomeChef> readHomeChef(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return homeChefStorage.readHomeChef(filePath);
    }

    @Override
    public void saveHomeChef(ReadOnlyHomeChef homeChef) throws IOException {
        saveHomeChef(homeChef, homeChefStorage.getHomeChefFilePath());
    }

    @Override
    public void saveHomeChef(ReadOnlyHomeChef homeChef, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        homeChefStorage.saveHomeChef(homeChef, filePath);
    }

}
