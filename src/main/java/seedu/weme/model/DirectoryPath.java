package seedu.weme.model;


import seedu.weme.commons.util.FileUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.AppUtil.checkArgument;

/**
 * Wrapper class for Path for Import and Export.
 */
public class DirectoryPath {

    public static final String MESSAGE_CONSTRAINTS = "File not found or invalid file path given.";

    public final Path filePath;

    /**
     * Constructs an {@code DirectoryPath}.
     *
     * @param directoryPath A valid relative file path.
     */
    public DirectoryPath(String directoryPath) {
        requireNonNull(directoryPath);
        checkArgument(isValidDirectoryPath(directoryPath), MESSAGE_CONSTRAINTS);
        this.filePath = Paths.get(directoryPath);
    }

    /**
     * Returns true if the given string is a valid Path.
     */
    public static boolean isValidDirectoryPath(String test) {
        // Paths.get() throws InvalidPathException when the path is a invalid.
        // It is caught and becomes return false.
        try {
            return FileUtil.isValidPath(test);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public Path getFilePath() {
        return filePath;
    }

    /**
     * Returns a URL object representing this {@code DirectoryPath}.
     *
     * @return a URL object representing this {@code DirectoryPath}
     */
    public URL toUrl() {
        try {
            return filePath.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String toString() {
        return filePath.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.weme.model.DirectoryPath // instanceof handles nulls
                && toString().equals(((seedu.weme.model.DirectoryPath) other).toString())); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
