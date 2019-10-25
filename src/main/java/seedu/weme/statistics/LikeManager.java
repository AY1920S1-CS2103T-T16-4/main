package seedu.weme.statistics;

import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.ModelManager;
import seedu.weme.model.meme.Meme;

/**
 * Implementation of Like interface.
 */
public class LikeManager {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final int INCREMENT = 1;
    private static final int DECREMENT = -1;

    private LikeData data;

    public LikeManager(LikeData data) {
        super();
        requireAllNonNull(data);

        logger.fine("Initializing with like data: " + data);

        this.data = new LikeData();
        this.data.setLikeMap(data.getObservableLikeData());
    }

    public LikeManager() {
        this.data = new LikeData();
    }

    public int getLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        return data.getLikesByMemeRef(memeRef);
    }

    /**
     * Returns an unmodifiable view of {@code LikeData}.
     */
    public LikeData getLikeData() {
        return data;
    }

    /**
     * Returns an unmodifiable view of {@code LikeData}.
     */
    public ObservableMap<String, Integer> getObservableLikeData() {
        return data.getObservableLikeData();
    }

    /**
     * Returns {@code LikeData} in Map.
     */
    public Map<String, Integer> getLikeDataInMap() {
        return data.getInMap();
    }

    /**
     * Replace the current like data with a new set of data.
     */
    public void setLikeData(LikeData replacement) {
        data.setLikeMap(replacement.getObservableLikeData());
    }

    /**
     * Replace the current like data with a new set of data in map.
     * @param replacement
     */
    public void setLikeDataFromMap(Map<String, Integer> replacement) {
        data.setLikeMap(FXCollections.observableMap(replacement));
    }

    /**
     * Increments a meme's like count by 1.
     */
    public void incrementMemeLikeCount(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, INCREMENT);
    }

    /**
     * Decrements a meme's like count by 1.
     */
    public void decrementLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.setLikesByMemeRef(memeRef, DECREMENT);
    }

    /**
     * Deletes like data of a meme when it gets deleted.
     */
    public void deleteLikesByMeme(Meme meme) {
        String memeRef = meme.getFilePath().toString();
        data.deleteLikesByMemeRef(memeRef);
    }

}
