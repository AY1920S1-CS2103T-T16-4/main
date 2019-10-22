package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.io.IOException;

import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.DirectoryPath;
import seedu.weme.model.Model;

/**
 * Loads memes from a directory to the import context.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads memes to the import staging area from a given directory. "
            + "Parameters: "
            + PREFIX_FILEPATH + "PATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:/Users/username/Downloads/ ";
    public static final String MESSAGE_SUCCESS = "Memes loaded successfully to the import staging area.";

    private final DirectoryPath importPath;

    /**
     * Creates a LoadCommand to import the memes from the specified {@code Path}.
     */
    public LoadCommand(DirectoryPath path) {
        requireNonNull(path);
        importPath = path;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.loadMeme(importPath);
        } catch (IOException e) {
            throw new CommandException(e.toString());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoadCommand // instanceof handles nulls
                && importPath.equals(((LoadCommand) other).importPath));
    }

}
