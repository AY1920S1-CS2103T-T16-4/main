package seedu.weme.statistics;

import java.util.List;
import java.util.PriorityQueue;
import javafx.collections.ObservableMap;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Meme;

/**
 * Interface for statistics data for Weme.
 */
public interface Stats {

    //============= Like Data ====================================

    LikeData getLikeManager();

    void setLikeManager(LikeData likeManager);

    ObservableMap<String, Integer> getObservableLikeData();

    void incrementMemeLikeCount(Meme meme);

    void deleteLikesByMeme(Meme meme);

    //============= Tag Data ====================================

    void parseMemeBookForTags(ReadOnlyMemeBook memeBook);

    List<TagWithCount> getTagsWithCountList();

    PriorityQueue<TagWithCount> getTagsInOrderOfCounts();

    PriorityQueue<TagWithCount> getTagsInOrderOfCounts(ReadOnlyMemeBook memeBook);
}
