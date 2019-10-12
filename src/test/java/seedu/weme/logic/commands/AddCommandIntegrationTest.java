package seedu.weme.logic.commands;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.weme.testutil.TypicalMemes.getTypicalMemeBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;
import seedu.weme.model.UserPrefs;
import seedu.weme.model.meme.Meme;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMemeBook(), new UserPrefs());
    }

    @Test
    public void execute_duplicateMeme_throwsCommandException() {
        Meme memeInList = model.getMemeBook().getMemeList().get(0);
        assertCommandFailure(new AddCommand(memeInList), model, AddCommand.MESSAGE_DUPLICATE_MEME);
    }

}
