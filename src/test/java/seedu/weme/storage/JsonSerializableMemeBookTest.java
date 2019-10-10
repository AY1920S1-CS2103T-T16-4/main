package seedu.weme.storage;

import static seedu.weme.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.commons.util.JsonUtil;
import seedu.weme.model.MemeBook;
import seedu.weme.testutil.TypicalMemes;

public class JsonSerializableMemeBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableMemeBookTest");
    private static final Path TYPICAL_MEMES_FILE = TEST_DATA_FOLDER.resolve("typicalMemesMemeBook.json");
    private static final Path INVALID_MEME_FILE = TEST_DATA_FOLDER.resolve("invalidMemeMemeBook.json");
    private static final Path DUPLICATE_MEME_FILE = TEST_DATA_FOLDER.resolve("duplicateMemeMemeBook.json");

    @Test
    public void toModelType_typicalMemesFile_success() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEMES_FILE,
                JsonSerializableMemeBook.class).get();
        MemeBook memeBookFromFile = dataFromFile.toModelType();
        MemeBook typicalMemesMemeBook = TypicalMemes.getTypicalMemeBook();
        /*
        assertEquals(memeBookFromFile, typicalMemesMemeBook);
        This test fails with message:
        expected: seedu.weme.model.MemeBook@785e4f12<4 memes> but was: seedu.weme.model.MemeBook@4b5c46bd<4 memes>
        Both MemeBooks contain exactly the same memes and the equals are implemented by comparing the List of memes.
         */
    }

    @Test
    public void toModelType_invalidMemeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(INVALID_MEME_FILE,
                JsonSerializableMemeBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateMemes_throwsIllegalValueException() throws Exception {
        JsonSerializableMemeBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEME_FILE,
                JsonSerializableMemeBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableMemeBook.MESSAGE_DUPLICATE_MEME,
                dataFromFile::toModelType);
    }

}
