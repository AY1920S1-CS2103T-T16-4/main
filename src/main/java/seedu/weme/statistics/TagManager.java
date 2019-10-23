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

    public PriorityQueue<TagWithCount> getTagsWithCountInPQ() {
        return tagsWithCount;
    }

    public PriorityQueue<TagWithCount> getTagsInOrderOfCounts(ReadOnlyMemeBook memeBook) {
        resetData();
        parseMemeBookForTags(memeBook);
        return tagsWithCount;
    }

    public List<TagWithCount> getTagsWithCountList() {
        List<TagWithCount> tagWithCountList = new ArrayList<>();
        PriorityQueue<TagWithCount> temp = getTagsWithCountInPQ();

        while (!tagsWithCount.isEmpty()) {
            tagWithCountList.add(temp.poll());
        }

        return tagWithCountList;
    }

    public void setTags(Set<Tag> tags) {
        resetData();
        tags.addAll(tags);
    }

    public void resetData() {
        tags.clear();
        tagsWithCount.clear();
    }

    public void parseMemeBookForTags(ReadOnlyMemeBook memeBook) {
        resetData();
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
