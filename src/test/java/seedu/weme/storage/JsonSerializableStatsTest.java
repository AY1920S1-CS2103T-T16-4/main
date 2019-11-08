package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.model.Weme;
import seedu.weme.model.statistics.Stats;
import seedu.weme.testutil.TypicalWeme;

class JsonSerializableStatsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableStatsTest");
    private static final Path TYPICAL_WEME_FILE = TEST_DATA_FOLDER.resolve("typicalWeme.json");
    private static final Path INVALID_LIKE_FILE = TEST_DATA_FOLDER.resolve("invalidLikeWeme.json");
    private static final Path INVALID_DISLIKE_FILE = TEST_DATA_FOLDER.resolve("invalidDislikeWeme.json");

    @Test
    public void toModelType_typicalMemesFile_success() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(TYPICAL_WEME_FILE,
                JsonSerializableWeme.class).get();
        JsonSerializableStats statsFromFile = dataFromFile.getStats();
        Stats statsFromFileInModel = statsFromFile.toModelType();
        Weme typicalWeme = TypicalWeme.getTypicalWeme();
        Stats typicalStats = typicalWeme.getStats();
        assertEquals(statsFromFileInModel, typicalStats);
    }

    @Test
    public void toModelType_invalidLikeData_throwsIllegalValueException() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(INVALID_LIKE_FILE,
                JsonSerializableWeme.class).get();
        JsonSerializableStats statsFromFile = dataFromFile.getStats();
        assertThrows(IllegalValueException.class, statsFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidTemplates_throwsIllegalValueException() throws Exception {
        JsonSerializableWeme dataFromFile = JsonUtil.readJsonFile(INVALID_DISLIKE_FILE,
                JsonSerializableWeme.class).get();
        JsonSerializableStats statsFromFile = dataFromFile.getStats();
        assertThrows(IllegalValueException.class, statsFromFile::toModelType);
    }

}
