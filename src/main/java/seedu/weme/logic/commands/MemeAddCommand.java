package seedu.weme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.IOException;
import java.nio.file.Path;

import seedu.weme.commons.util.FileUtil;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.Model;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;

/**
 * Adds a meme to the meme book.
 */
public class MemeAddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meme to weme. "
            + "Parameters: "
            + PREFIX_FILEPATH + "PATH "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_FILEPATH + "C:\\Users\\username\\Downloads\\funny_meme.jpg "
            + PREFIX_DESCRIPTION + "Popular Meme among SoC Students  "
            + PREFIX_TAG + "funny";

    public static final String MESSAGE_SUCCESS = "New meme added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEME = "This meme already exists in weme";

    private final Meme toAdd;

    /**
     * Creates an MemeAddCommand to add the specified {@code Meme}
     */
    public MemeAddCommand(Meme meme) {
        requireNonNull(meme);
        toAdd = meme;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMeme(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEME);
        }

        Meme relativePathMeme = createRelativePathMeme(toAdd);
        model.addMeme(relativePathMeme);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Creates a meme with a relative path as its ImagePath.
     * @param meme the meme to be reassigned a relative path.
     * @return new meme with relative path.
     * @throws CommandException when an error has occurred.
     */
    private Meme createRelativePathMeme(Meme meme) throws CommandException {
        try {
            String filename = meme.getFilePath().getFileName();
            Path newPath = FileUtil.generateRelativePath(
                    filename.contains(".") ? filename.substring(filename.lastIndexOf(".")) : "");
            FileUtil.copyFile(meme.getFilePath().getFilePath(), newPath);
            ImagePath newImagePath = new ImagePath(newPath.toString());
            return new Meme(newImagePath, meme.getDescription(), meme.getTags());
        } catch (IOException ioe) {
            throw new CommandException("Error encountered while trying to convert path: " + ioe);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MemeAddCommand // instanceof handles nulls
                && toAdd.equals(((MemeAddCommand) other).toAdd));
    }
}
