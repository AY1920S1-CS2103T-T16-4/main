package seedu.weme.logic.commands;

import javafx.collections.ObservableList;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.DirectoryPath;
import seedu.weme.model.Model;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.util.MemeUtil;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;

public class MemeExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports memes from staging area to a given directory. "
            + "Parameters: "
            + PREFIX_FILEPATH + "PATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:\\Users\\username\\Downloads\\funny_meme.jpg ";
    public static final String MESSAGE_SUCCESS = "Memes exported successfully!";
    public static final String MESSAGE_EXPORT_FAILURE = "Error encountered while exporting the meme to data folder";

    private final DirectoryPath exportPath;

    /**
     * Creates an MemeExportCommand to export the memes from the specified {@code Path}.
     */
    public MemeExportCommand(DirectoryPath path) {
        requireNonNull(path);
        exportPath = path;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        /*
        // Copy the meme to internal data directory
        Meme copiedMeme;
        try {
            copiedMeme = MemeUtil.copyMeme(toAdd, model.getMemeImagePath());
        } catch (FileAlreadyExistsException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_COPY_FAILURE);
        }

        if (model.hasMeme(copiedMeme)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        model.addMeme(copiedMeme);
        model.commitMemeBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS, copiedMeme));
        */

        try {
            MemeUtil.exportMeme(model.getFilteredStagedMemeList());
        } catch (IOException e) {
            throw new CommandException(MESSAGE_EXPORT_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeExportCommand // instanceof handles nulls
                && exportPath.equals(((MemeExportCommand) other).exportPath));
    }

}
