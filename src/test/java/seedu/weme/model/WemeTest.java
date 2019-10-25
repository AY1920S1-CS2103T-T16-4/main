package seedu.weme.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_FILEPATH_JOKER;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_JOKER;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.DOGE;
import static seedu.weme.testutil.TypicalMemes.JOKER;
import static seedu.weme.testutil.TypicalWeme.getTypicalWeme;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.exceptions.DuplicateMemeException;
import seedu.weme.model.template.Template;
import seedu.weme.statistics.Stats;
import seedu.weme.statistics.StatsManager;
import seedu.weme.testutil.MemeBuilder;

public class WemeTest {

    private final Weme weme = new Weme();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), weme.getMemeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weme.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyWeme_replacesData() {
        Weme newData = getTypicalWeme();
        weme.resetData(newData);
        assertEquals(newData, weme);
    }

    @Test
    public void resetData_withDuplicateMemes_throwsDuplicateMemeException() {
        // Two memes with the same identity fields
        Meme editedAlice = new MemeBuilder(JOKER).withDescription(VALID_DESCRIPTION_JOKER)
                .withFilePath(VALID_FILEPATH_JOKER).withTags(VALID_TAG_JOKER).build();
        List<Meme> newMemes = Arrays.asList(JOKER, editedAlice);
        WemeStub newData = new WemeStub();
        newData.setMemes(newMemes);

        assertThrows(DuplicateMemeException.class, () -> weme.resetData(newData));
    }

    @Test
    public void hasMeme_nullMeme_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> weme.hasMeme(null));
    }

    @Test
    public void hasMeme_memeNotInWeme_returnsFalse() {
        assertFalse(weme.hasMeme(DOGE));
    }

    @Test
    public void hasMeme_memeInWeme_returnsTrue() {
        weme.addMeme(DOGE);
        assertTrue(weme.hasMeme(DOGE));
    }

    @Test
    public void hasMeme_memeWithSameIdentityFieldsInWeme_returnsTrue() {
        weme.addMeme(JOKER);
        Meme editedBob = new MemeBuilder(JOKER).withDescription(VALID_DESCRIPTION_JOKER)
                .withFilePath(VALID_FILEPATH_JOKER)
                .withTags(VALID_TAG_JOKER).build();
        assertTrue(weme.hasMeme(editedBob));
    }

    @Test
    public void getMemeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> weme.getMemeList().remove(0));
    }

    /**
     * A stub ReadOnlyWeme whose memes list can violate interface constraints.
     */
    private static class WemeStub implements ReadOnlyWeme {
        private final ObservableList<Meme> memes = FXCollections.observableArrayList();
        private final ObservableList<Meme> stagedMemes = FXCollections.observableArrayList();
        private final ObservableList<Meme> importList = FXCollections.observableArrayList();
        private final ObservableList<Template> templates = FXCollections.observableArrayList();
        private final Stats stats = new StatsManager();

        WemeStub() {
        }

        void setMemes(List<Meme> memes) {
            this.memes.setAll(memes);
        }

        @Override
        public Stats getStats() {
            return stats;
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
