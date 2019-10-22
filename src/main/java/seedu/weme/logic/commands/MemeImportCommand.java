package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;

/**
 * Imports memes from the import context into application storage.
 */
public class MemeImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports memes from import staging area into application storage. ";
    public static final String MESSAGE_SUCCESS = "Memes imported successfully to storage.";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.importMeme();
        } catch (IOException e) {
            throw new CommandException(e.toString());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeImportCommand); // instanceof handles nulls
    }

}
