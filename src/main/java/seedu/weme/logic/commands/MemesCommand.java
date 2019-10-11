package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;

/**
 * Switches context to the Memes context.
 */
public class MemesCommand extends Command {

    public static final String COMMAND_WORD = "memes";

    public static final String MESSAGE_SUCCESS = "Switched to Memes context.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setContext(ModelContext.CONTEXT_MEMES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
