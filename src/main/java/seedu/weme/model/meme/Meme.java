package seedu.weme.model.meme;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.weme.model.tag.Tag;

/**
 * Represents a Meme in the meme book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Meme {

    // Identity fields
    private final Name name;

    // Data fields
    private final Address address;
    private final ImageUrl url;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Meme(Name name, Address address, ImageUrl url, Set<Tag> tags) {
        requireAllNonNull(name, address, url, tags);
        this.name = name;
        this.address = address;
        this.url = url;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public ImageUrl getUrl() {
        return url;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both memes of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two memes.
     */
    public boolean isSameMeme(Meme otherMeme) {
        if (otherMeme == this) {
            return true;
        }

        return otherMeme != null
                && otherMeme.getName().equals(getName());
    }

    /**
     * Returns true if both memes have the same identity and data fields.
     * This defines a stronger notion of equality between two memes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Meme)) {
            return false;
        }

        Meme otherMeme = (Meme) other;
        return otherMeme.getName().equals(getName())
                && otherMeme.getAddress().equals(getAddress())
                && otherMeme.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, url, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Address: ")
                .append(getAddress())
                .append(" URL: ")
                .append(getUrl())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
