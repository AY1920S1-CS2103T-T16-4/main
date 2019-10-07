package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.ALICE;
import static seedu.weme.testutil.TypicalMemes.BOB;

import org.junit.jupiter.api.Test;

import seedu.weme.testutil.MemeBuilder;

public class MemeTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Meme meme = new MemeBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> meme.getTags().remove(0));
    }

    @Test
    public void isSameUrl() {
        // same object -> returns true
        assertTrue(ALICE.isSameMeme(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameMeme(null));

        // same name, different attributes -> returns true
        Meme editedAlice = new MemeBuilder(ALICE).withDescription(VALID_DESCRIPTION_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMeme(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Meme aliceCopy = new MemeBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different meme -> returns false
        assertFalse(ALICE.equals(BOB));

        // different url -> returns false
        Meme editedAlice = new MemeBuilder(ALICE).withUrl(VALID_URL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new MemeBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
