package seedu.weme.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Tag manager in Statistics feature that reads tags from a {@code MemeBook}.
 */
public class TagManager {

    private Set<Tag> tags;
    private PriorityQueue<TagWithCount> tagsWithCount;

    public TagManager() {
        tags = new HashSet<>();
        tagsWithCount = new PriorityQueue<>();
    }

    public Set<Tag> getTagsInSet() {
        return tags;
    }

    /**
     * Get {@code TagWithCount} in PriorityQueue from the pre-stored data.
     */
    public PriorityQueue<TagWithCount> getTagsWithCountInPriorityQ() {
        return tagsWithCount;
    }

    /**
     * Get {@code TagWithCount} in PriorityQueue from a {@code ReadOnlyMemeBook}.
     */
    public PriorityQueue<TagWithCount> getTagsInOrderOfCounts(ReadOnlyMemeBook memeBook) {
        purgeData();
        parseMemeBookForTags(memeBook);
        return tagsWithCount;
    }

    /**
     * Returns {@code TagWithCount} in List.
     */
    public List<TagWithCount> getTagsWithCountList() {
        List<TagWithCount> tagWithCountList = new ArrayList<>();
        PriorityQueue<TagWithCount> temp = getTagsWithCountInPriorityQ();

        while (!tagsWithCount.isEmpty()) {
            tagWithCountList.add(temp.poll());
        }

        return tagWithCountList;
    }

    /**
     * Resets the data.
     */
    public void purgeData() {
        tags.clear();
        tagsWithCount.clear();
    }

    /**
     * Parses a {@code ReadOnlyMemeBook} for tags.
     */
    public void parseMemeBookForTags(ReadOnlyMemeBook memeBook) {
        purgeData();
        ObservableList<Meme> memeList = memeBook.getMemeList();
        Map<Tag, Integer> tagToCount = new HashMap<>();
        Set<Tag> memeTags;

        for (Meme meme : memeList) {
            memeTags = meme.getTags();
            tags.addAll(memeTags);
            for (Tag tag : memeTags) {
                int count = tagToCount.getOrDefault(tag, 0);
                tagToCount.put(tag, count + 1);
            }
        }

        for (Map.Entry<Tag, Integer> mapEntry : tagToCount.entrySet()) {
            tagsWithCount.offer(new TagWithCount(mapEntry.getKey(), mapEntry.getValue()));
        }
    }

}
