package seedu.weme.testutil;

import static seedu.weme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_URL;

import java.util.Set;

import seedu.weme.logic.commands.AddCommand;
import seedu.weme.logic.commands.EditCommand.EditMemeDescriptor;
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
        return AddCommand.COMMAND_WORD + " " + getMemeDetails(meme);
    }

    /**
     * Returns the part of command string for the given {@code meme}'s details.
     */
    public static String getMemeDetails(Meme meme) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + meme.getName().fullName + " ");
        sb.append(PREFIX_ADDRESS + meme.getAddress().value + " ");
        sb.append(PREFIX_URL + meme.getUrl().value + " ");
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
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getUrl().ifPresent(url -> sb.append(PREFIX_URL).append(url.value).append(" "));
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
}
