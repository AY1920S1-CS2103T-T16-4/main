package seedu.weme.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * Tag manager in Statistics feature that reads tags from a {@code MemeBook}.
 */
public class TagManager {

    public static final int INITIAL_LIKE_COUNT = 0;

    private final Set<Tag> tags;
    private final ObservableList<TagWithCount> tagsWithCount;
    private final ObservableList<TagWithLike> tagsWithLike;

    public TagManager() {
        tags = new HashSet<>();
        tagsWithCount = new ArrayList<>();
        tagsWithLike = new ArrayList<>();
    }

    public Set<Tag> getTagsInSet() {
        return tags;
    }

    /**
     * Returns {@code TagWithCount} in List.
     */
    public ObservableList<TagWithCount> getTagsWithCountList(ObservableList<Meme> memeList) {
        parseMemeListForTags(memeList);
        return FXCollections.observableList(tagsWithCount);
    }

    public ObservableList<TagWithLike> getTagsWithLike(ObservableList<Meme> memeList, LikeManager likeData) {
        parseMemeListAndLikeDataForTags(memeList, likeData);
        return FXCollections.observableList(tagsWithLike);
    }

    /**
     * Resets the data.
     */
    public void purgeData() {
        tags.clear();
        tagsWithCount.clear();
        tagsWithLike.clear();
    }

    /**
     * Parses a {@code ReadOnlyMemeBook} for tags and their occurrence.
     */
    public void parseMemeListForTags(ObservableList<Meme> memeList) {
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
            tagsWithCount.add(new TagWithCount(mapEntry.getKey(), mapEntry.getValue()));
        }

        Collections.sort(tagsWithCount);
    }

    /**
     * Parses a {@code ReadOnlyMemeBook} for tags and their like counts.
     */
    public void parseMemeListAndLikeDataForTags(ObservableList<Meme> memeList, LikeManager likeData) {
        purgeData();
        Map<Tag, Integer> tagToLike = new HashMap<>();
        int likeCount;

        for (Meme meme : memeList) {
            likeCount = likeData.getLikesByMeme(meme);
            tags.addAll(meme.getTags());
            for (Tag tag : tags) {
                tagToLike.put(tag, tagToLike.getOrDefault(tag, INITIAL_LIKE_COUNT) + likeCount);
            }
        }

        for (Map.Entry<Tag, Integer> mapEntry : tagToLike.entrySet()) {
            tagsWithLike.add(new TagWithLike(mapEntry.getKey(), mapEntry.getValue()));
        }

        Collections.sort(tagsWithLike);
    }

}
