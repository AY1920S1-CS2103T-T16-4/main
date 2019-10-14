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
 * Contains integration tests (interaction with the Model) for {@code MemeAddCommand}.
 */
public class MemeAddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMemeBook(), new UserPrefs());
    }


    /* Deprecated since random uuid generation will result in indeterministic meme path.
    @Test
    public void execute_newMeme_success() {
        Meme validMeme = new MemeBuilder().build();

        Model expectedModel = new ModelManager(model.getMemeBook(), new UserPrefs());
        expectedModel.addMeme(validMeme);

        assertCommandSuccess(new MemeAddCommand(validMeme), model,
                String.format(MemeAddCommand.MESSAGE_SUCCESS, validMeme), expectedModel);
    }
     */

    @Test
    public void execute_duplicateMeme_throwsCommandException() {
        Meme memeInList = model.getMemeBook().getMemeList().get(0);
        assertCommandFailure(new MemeAddCommand(memeInList), model, MemeAddCommand.MESSAGE_DUPLICATE_MEME);
    }

}
