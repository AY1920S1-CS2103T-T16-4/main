package seedu.weme.logic.commands.generalcommand;

import static seedu.weme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;
import seedu.weme.model.ModelManager;

public class TabCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_tabMemes_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(TabCommand.MESSAGE_SUCCESS,
                CONTEXT_MEMES.getContextName()));
        assertCommandSuccess(new TabCommand(CONTEXT_MEMES), model, expectedCommandResult, expectedModel);
    }
}
