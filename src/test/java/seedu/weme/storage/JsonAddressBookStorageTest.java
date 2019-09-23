package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.ALICE;
import static seedu.weme.testutil.TypicalMemes.HOON;
import static seedu.weme.testutil.TypicalMemes.IDA;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.weme.commons.exceptions.DataConversionException;
import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyMemeBook> readAddressBook(String filePath) throws Exception {
        return new JsonMemeBookStorage(Paths.get(filePath)).readMemeBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidMemeMemeBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        MemeBook original = getTypicalMemeBook();
        JsonMemeBookStorage jsonAddressBookStorage = new JsonMemeBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveMemeBook(original, filePath);
        ReadOnlyMemeBook readBack = jsonAddressBookStorage.readMemeBook(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMeme(HOON);
        original.removeMeme(ALICE);
        jsonAddressBookStorage.saveMemeBook(original, filePath);
        readBack = jsonAddressBookStorage.readMemeBook(filePath).get();
        assertEquals(original, new MemeBook(readBack));

        // Save and read without specifying file path
        original.addMeme(IDA);
        jsonAddressBookStorage.saveMemeBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readMemeBook().get(); // file path not specified
        assertEquals(original, new MemeBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyMemeBook addressBook, String filePath) {
        try {
            new JsonMemeBookStorage(Paths.get(filePath))
                    .saveMemeBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new MemeBook(), null));
    }
}
