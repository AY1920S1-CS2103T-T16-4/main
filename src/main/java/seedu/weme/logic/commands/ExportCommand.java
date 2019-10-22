package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.io.IOException;

import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.DirectoryPath;
import seedu.weme.model.Model;

/**
 * Exports memes from staging area to a specified path.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports memes in the export context to a given directory. "
            + "Parameters: "
            + PREFIX_FILEPATH + "PATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:/Users/username/Downloads/funny_meme.jpg ";
    public static final String MESSAGE_SUCCESS = "Memes exported successfully!";

    private final DirectoryPath exportPath;

    /**
     * Creates an ExportCommand to export the memes from the specified {@code Path}.
     */
    public ExportCommand(DirectoryPath path) {
        requireNonNull(path);
        exportPath = path;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.exportMeme(exportPath);
        } catch (IOException e) {
            throw new CommandException(e.toString());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && exportPath.equals(((ExportCommand) other).exportPath));
    }

}
