package seedu.weme.model.meme;

import java.net.MalformedURLException;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.AppUtil.checkArgument;

/**
 * Wrapper class for URL in {@code Meme}.
 */
public class ImageUrl {

    public static final String MESSAGE_CONSTRAINTS = "Url must be in valid format and cannot be blank";

    /**
     * The first character of the URL must not be a whitespace.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private final URL url;
    public final String value;

    /**
     * Constructs an {@code Url}.
     *
     * @param url A valid url.
     */
    public ImageUrl(String url) {
        requireNonNull(url);
        checkArgument(isValidUrl(url), MESSAGE_CONSTRAINTS);
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.value = url;
    }

    /**
     * Returns true if the given string is a valid URL.
     */
    public static boolean isValidUrl(String test) {
        return test.matches((VALIDATION_REGEX));
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImageUrl // instanceof handles nulls
                && value.equals(((ImageUrl) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}