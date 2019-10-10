package seedu.weme.model.meme;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Wrapper class for URL in {@code Meme}.
 * Acts as the identity reference to a {@code Meme}.
 */
public class ImagePath {

    public static final String MESSAGE_CONSTRAINTS = "File not found or invalid file path given";

    /**
     * The first character of the URL must not be a whitespace.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;
    public final Path filePath;

    /**
     * Constructs an {@code ImagePath}.
     *
     * @param relativeFilePath A valid relative file path.
     */
    public ImagePath(String relativeFilePath) {
        requireNonNull(relativeFilePath);
        checkArgument(isValidFilePath(relativeFilePath), MESSAGE_CONSTRAINTS);
        this.filePath = Paths.get(relativeFilePath);
        this.value = relativeFilePath;
    }

    /**
     * Returns true if the given string is a valid URL.
     */
    public static boolean isValidFilePath(String test) {
        try {
            return test.matches(VALIDATION_REGEX) &&
                    (new File(Paths.get(test).toAbsolutePath().toUri())).exists());
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImagePath // instanceof handles nulls
                && value.equals(((ImagePath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
