package seedu.weme.statistics;

import seedu.weme.model.tag.Tag;

public class TagWithCount implements Comparable<TagWithCount> {
    private Tag tag;
    private int count;

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