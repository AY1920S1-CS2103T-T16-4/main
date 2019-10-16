package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.weme.commons.core.Messages;
import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;

import java.util.List;


/**
 * Stage Command.
 */
public class MemeStageCommand extends Command {

    public static final String COMMAND_WORD = "stage";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Stages a meme. "
            + "Parameters: "
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "New meme staged: %1$s";

    private final Index targetIndex;

    /**
     * Creates an MemeStageCommand to add the specified {@code Meme}
     */
    public MemeStageCommand(Index index) {
        requireNonNull(index);
        targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        /*
        if (model.hasMeme(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.addMeme(toAdd);
        */
        List<Meme> lastShownList = model.getFilteredMemeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEME_DISPLAYED_INDEX);
        }

        Meme memetoStage = lastShownList.get(targetIndex.getZeroBased());
        //model.stageMeme(memetoStage);
        //model.commitMemeBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, memetoStage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeStageCommand // instanceof handles nulls
                && targetIndex.equals(((MemeStageCommand) other).targetIndex));
    }
}
