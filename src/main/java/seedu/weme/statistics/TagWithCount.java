package seedu.weme.statistics;

import seedu.weme.model.tag.Tag;

/**
 * A wrapper class of {@code Tag} that carries the number of occurrence of such a tag
 * in a {@code MemeBook}for {@code TagManager} in statistics.
 */
public class TagWithCount implements Comparable<TagWithCount> {
    private Tag tag;
    private int count;

    /**
     * Constructs a {@code TagWithCount}
     * @param tag
     * @param count
     */
    public TagWithCount(Tag tag, int count) {
        this.tag = tag;
        this.count = count;
    }

    public Tag getTag() {
        return tag;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return tag.tagName + " count: " + count;
    }

    @Override
    public int compareTo(TagWithCount o) {
        if (this.count > o.count) {
            return 1;
        } else if (this.count < o.count) {
            return -1;
        } else {
            return tag.tagName.compareTo(o.tag.tagName);
        }
    }
}
