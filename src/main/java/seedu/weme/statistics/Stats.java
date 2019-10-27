package seedu.weme.statistics;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import seedu.weme.model.meme.Meme;

/**
 * Interface for statistics data for Weme.
 */
public interface Stats {

    //============= Like Data ====================================

    LikeData getLikeData();

    void setLikeData(LikeData likeData);

    ObservableMap<String, Integer> getObservableLikeData();

    void incrementMemeLikeCount(Meme meme);

    void deleteLikesByMeme(Meme meme);

    void resetData(Stats stats);

    //============= Tag Data ====================================

    /**
     * Returns a list of tags with their use counts in descending order.
     */
    ObservableList<TagWithCount> getTagsWithCountList(ObservableList<Meme> memeList);

    /**
     * Returns a list of tags with their like counts in descending order.
     */
    ObservableList<TagWithLike> getTagsWithLikeCountList(ObservableList<Meme> memeList);

}
