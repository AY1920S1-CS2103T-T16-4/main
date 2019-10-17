package seedu.weme.testutil;

import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.weme.model.util.MemeUtil.copyMeme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import seedu.weme.logic.commands.MemeAddCommand;
import seedu.weme.logic.commands.MemeEditCommand.EditMemeDescriptor;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * A utility class for Meme.
 */
public class MemeUtil {

    /**
     * Returns an add command string for adding the {@code meme}.
     */
    public static String getAddCommand(Meme meme) {
        return MemeAddCommand.COMMAND_WORD + " " + getMemeDetails(meme);
    }

    /**
     * Returns the part of command string for the given {@code meme}'s details.
     */
    public static String getMemeDetails(Meme meme) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_FILEPATH + meme.getFilePath().toString() + " ");
        sb.append(PREFIX_DESCRIPTION + meme.getDescription().value + " ");
        meme.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMemeDescriptor}'s details.
     */
    public static String getEditMemeDescriptorDetails(EditMemeDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getFilePath().ifPresent(path -> sb.append(PREFIX_FILEPATH).append(path.toString()).append(" "));
        descriptor.getDescription().ifPresent(description ->
                sb.append(PREFIX_DESCRIPTION).append(description.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     * Similar to {@link seedu.weme.model.util.MemeUtil#copyMeme(Meme, Path)}, except this method deletes the copied
     * image file immediately afterwards.
     *
     * @param toCopy       the {@code Meme} to copy
     * @param memeLocation the meme image location
     * @return a new {@code Meme} with the new {@code ImagePath}.
     */
    public static Meme generateCopiedMeme(Meme toCopy, Path memeLocation) throws IOException {
        Meme copied = copyMeme(toCopy, memeLocation);
        Files.delete(copied.getFilePath().getFilePath());
        return copied;
    }

}
