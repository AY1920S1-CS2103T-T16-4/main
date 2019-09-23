package seedu.weme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.weme.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.weme.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.EditCommand.EditMemeDescriptor;
import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;
import seedu.weme.testutil.EditPersonDescriptorBuilder;
import seedu.weme.testutil.MemeBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalMemeBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Meme editedMeme = new MemeBuilder().build();
        EditCommand.EditMemeDescriptor descriptor = new EditPersonDescriptorBuilder(editedMeme).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(model.getFilteredMemeList().get(0), editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredMemeList().size());
        Meme lastMeme = model.getFilteredMemeList().get(indexLastPerson.getZeroBased());

        MemeBuilder personInList = new MemeBuilder(lastMeme);
        Meme editedMeme = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditMemeDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(lastMeme, editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditCommand.EditMemeDescriptor());
        Meme editedMeme = model.getFilteredMemeList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Meme memeInFilteredList = model.getFilteredMemeList().get(INDEX_FIRST_PERSON.getZeroBased());
        Meme editedMeme = new MemeBuilder(memeInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEME_SUCCESS, editedMeme);

        Model expectedModel = new ModelManager(new MemeBook(model.getMemeBook()), new UserPrefs());
        expectedModel.setMeme(model.getFilteredMemeList().get(0), editedMeme);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Meme firstMeme = model.getFilteredMemeList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditCommand.EditMemeDescriptor descriptor = new EditPersonDescriptorBuilder(firstMeme).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEME);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit meme in filtered list into a duplicate in
        Meme memeInList = model.getMemeBook().getMemeList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(memeInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEME);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemeList().size() + 1);
        EditCommand.EditMemeDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of  list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMemeBook().getMemeList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditMemeDescriptor copyDescriptor = new EditCommand.EditMemeDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
