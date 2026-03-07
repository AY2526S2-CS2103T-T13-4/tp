package seedu.homechef.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.model.ReadOnlyHomeChef;

/**
 * Represents a storage for {@link seedu.homechef.model.HomeChef}.
 */
public interface HomeChefStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getHomeChefFilePath();

    /**
     * Returns HomeChef data as a {@link ReadOnlyHomeChef}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyHomeChef> readHomeChef() throws DataLoadingException;

    /**
     * @see #getHomeChefFilePath()
     */
    Optional<ReadOnlyHomeChef> readHomeChef(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyHomeChef} to the storage.
     * @param homeChef cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveHomeChef(ReadOnlyHomeChef homeChef) throws IOException;

    /**
     * @see #saveHomeChef(ReadOnlyHomeChef)
     */
    void saveHomeChef(ReadOnlyHomeChef homeChef, Path filePath) throws IOException;

}
