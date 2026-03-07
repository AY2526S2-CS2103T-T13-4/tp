package seedu.homechef.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.homechef.commons.core.LogsCenter;
import seedu.homechef.commons.exceptions.DataLoadingException;
import seedu.homechef.commons.exceptions.IllegalValueException;
import seedu.homechef.commons.util.FileUtil;
import seedu.homechef.commons.util.JsonUtil;
import seedu.homechef.model.ReadOnlyHomeChef;

/**
 * A class to access HomeChef data stored as a json file on the hard disk.
 */
public class JsonHomeChefStorage implements HomeChefStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonHomeChefStorage.class);

    private Path filePath;

    public JsonHomeChefStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getHomeChefFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyHomeChef> readHomeChef() throws DataLoadingException {
        return readHomeChef(filePath);
    }

    /**
     * Similar to {@link #readHomeChef()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyHomeChef> readHomeChef(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableHomeChef> jsonHomeChef = JsonUtil.readJsonFile(
                filePath, JsonSerializableHomeChef.class);
        if (!jsonHomeChef.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonHomeChef.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveHomeChef(ReadOnlyHomeChef homeChef) throws IOException {
        saveHomeChef(homeChef, filePath);
    }

    /**
     * Similar to {@link #saveHomeChef(ReadOnlyHomeChef)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveHomeChef(ReadOnlyHomeChef homeChef, Path filePath) throws IOException {
        requireNonNull(homeChef);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableHomeChef(homeChef), filePath);
    }

}
