package seedu.weme.statistics;

import seedu.weme.model.tag.Tag;

/**
 * A wrapper class of {@code Tag} that carries the number of occurrence of such a tag
 * in a {@code MemeBook}for {@code TagManager} in statistics.
 */
public class TagWithLike implements Comparable<TagWithLike> {
    private Tag tag;
    private int like;

    /**
     * Constructs a {@code TagWithCount}
     * @param tag
     * @param like
     */
    public TagWithLike(Tag tag, int like) {
        this.tag = tag;
        this.like = like;
    }

    public Tag getTag() {
        return tag;
    }

    public int getLike() {
        return like;
    }

    @Override
    public String toString() {
        return tag.tagName + " likes: " + like;
    }

    @Override
    public int compareTo(TagWithLike o) {
        if (this.like > o.like) {
            return 1;
        } else if (this.like < o.like) {
            return -1;
        } else {
            return tag.tagName.compareTo(o.tag.tagName);
        }
    }
}
