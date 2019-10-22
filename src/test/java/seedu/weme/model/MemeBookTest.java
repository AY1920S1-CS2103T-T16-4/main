package seedu.weme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemeBook.getTypicalMemeBook;
import static seedu.weme.testutil.TypicalMemes.DOGE;
import static seedu.weme.testutil.TypicalMemes.JOKER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.exceptions.DuplicateMemeException;
import seedu.weme.model.template.Template;
import seedu.weme.testutil.MemeBuilder;

public class MemeBookTest {

    private final MemeBook memeBook = new MemeBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), memeBook.getMemeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> memeBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMemeBook_replacesData() {
        MemeBook newData = getTypicalMemeBook();
        memeBook.resetData(newData);
        assertEquals(newData, memeBook);
    }

    @Test
    public void resetData_withDuplicateMemes_throwsDuplicateMemeException() {
        // Two memes with the same identity fields
        Meme editedAlice = new MemeBuilder(JOKER).withDescription(VALID_DESCRIPTION_JOKER)
                .withFilePath(VALID_FILEPATH_JOKER).withTags(VALID_TAG_JOKER).build();
        List<Meme> newMemes = Arrays.asList(JOKER, editedAlice);
        MemeBookStub newData = new MemeBookStub();
        newData.setMemes(newMemes);

        assertThrows(DuplicateMemeException.class, () -> memeBook.resetData(newData));
    }

    @Test
    public void hasMeme_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> memeBook.hasMeme(null));
    }

    @Test
    public void hasMeme_memeNotInMemeBook_returnsFalse() {
        assertFalse(memeBook.hasMeme(DOGE));
    }

    @Test
    public void hasMeme_memeInMemeBook_returnsTrue() {
        memeBook.addMeme(DOGE);
        assertTrue(memeBook.hasMeme(DOGE));
    }

    @Test
    public void hasMeme_memeWithSameIdentityFieldsInMemeBook_returnsTrue() {
        memeBook.addMeme(JOKER);
        Meme editedBob = new MemeBuilder(JOKER).withDescription(VALID_DESCRIPTION_JOKER)
                .withFilePath(VALID_FILEPATH_JOKER)
                .withTags(VALID_TAG_JOKER).build();
        assertTrue(memeBook.hasMeme(editedBob));
    }

    @Test
    public void getMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> memeBook.getMemeList().remove(0));
    }

    /**
     * A stub ReadOnlyMemeBook whose memes list can violate interface constraints.
     */
    private static class MemeBookStub implements ReadOnlyMemeBook {
        private final ObservableList<Meme> memes = FXCollections.observableArrayList();
        private final ObservableList<Meme> stagedMemes = FXCollections.observableArrayList();
        private final ObservableList<Meme> importList = FXCollections.observableArrayList();
        private final ObservableList<Template> templates = FXCollections.observableArrayList();

        MemeBookStub() {
        }

        void setMemes(List<Meme> memes) {
            this.memes.setAll(memes);
        }

        @Override
        public ObservableList<Meme> getMemeList() {
            return memes;
        }

        @Override
        public ObservableList<Meme> getStagedMemeList() {
            return stagedMemes;
        }

        @Override
        public ObservableList<Meme> getImportList() {
            return importList;
        }

        public ObservableList<Template> getTemplateList() {
            return templates;
        }
    }

}
