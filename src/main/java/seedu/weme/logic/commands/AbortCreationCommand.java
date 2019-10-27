package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;

/**
 * Aborts the current meme creation session.
 */
public class AbortCreationCommand extends Command {

    public static final String COMMAND_WORD = "abort";
    public static final String MESSAGE_SUCCESS = "Meme creation aborted.";

    /**
     * Creates an TextAddCommand to add {@code MemeText} to the meme being created..
     */
    public AbortCreationCommand() {
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.abortMemeCreation();
        model.setContext(ModelContext.CONTEXT_TEMPLATES);
        model.commitWeme();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
