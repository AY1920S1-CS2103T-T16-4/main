package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

/**
 * Unstage Command.
 */
public class MemeUnstageCommand extends Command {

    public static final String COMMAND_WORD = "unstage";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": unstages a meme. "
            + "Parameters: "
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Meme unstaged: %1$s";

    private final Index targetIndex;

    /**
     * Creates an MemeStageCommand to add the specified {@code Meme}
     */
    public MemeUnstageCommand(Index index) {
        requireNonNull(index);
        targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Meme> lastShownList = model.getFilteredStagedMemeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memeToUnstage = lastShownList.get(targetIndex.getZeroBased());
        model.unstageMeme(memeToUnstage);
        model.commitMemeBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, memeToUnstage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeUnstageCommand // instanceof handles nulls
                && targetIndex.equals(((MemeUnstageCommand) other).targetIndex));
    }
}
