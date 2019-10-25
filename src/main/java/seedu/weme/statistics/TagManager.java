package seedu.weme.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Tag manager in Statistics feature that reads tags from a {@code MemeBook}.
 */
public class TagManager {

    public static final int INITIAL_LIKE_COUNT = 0;

    private Set<Tag> tags;
    private PriorityQueue<TagWithCount> tagsWithCount;

    public TagManager() {
        tags = new HashSet<>();
        tagsWithCount = new PriorityQueue<>();
    }

    /**
     * Returns {@code TagWithCount} in List.
     * @param memeList
     */
    public List<TagWithCount> getTagsWithCountList(ObservableList<Meme> memeList) {
        List<TagWithCount> tagWithCountList = new ArrayList<>();
        PriorityQueue<TagWithCount> temp = getTagsInOrderOfCounts(memeList);

        while (!tagsWithCount.isEmpty()) {
            tagWithCountList.add(temp.poll());
        }

        return tagWithCountList;
    }

    /**
     * Get {@code TagWithCount} in PriorityQueue from a {@code ReadOnlyMemeBook}.
     * @param memeList
     */
    public PriorityQueue<TagWithCount> getTagsInOrderOfCounts(ObservableList<Meme> memeList) {
        purgeData();
        parseWemeForTags(memeList);
        return tagsWithCount;
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
     * @param memeList
     */
    public void parseWemeForTags(ObservableList<Meme> memeList) {
        purgeData();
        Map<Tag, Integer> tagToCount = new HashMap<>();
        Set<Tag> memeTags;

        for (Meme meme : memeList) {
            memeTags = meme.getTags();
            tags.addAll(memeTags);
            for (Tag tag : memeTags) {
                int count = tagToCount.getOrDefault(tag, INITIAL_LIKE_COUNT);
                tagToCount.put(tag, count + 1);
            }
        }

        for (Map.Entry<Tag, Integer> mapEntry : tagToCount.entrySet()) {
            tagsWithCount.offer(new TagWithCount(mapEntry.getKey(), mapEntry.getValue()));
        }
    }

}
