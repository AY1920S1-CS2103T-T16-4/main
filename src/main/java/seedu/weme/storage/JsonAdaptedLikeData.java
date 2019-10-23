package seedu.weme.storage;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.LikeManager;

/**
 * Json-friendly version of {@link LikeData}.
 */
class JsonAdaptedLikeData {

    private Map<String, Integer> likeData = new HashMap<>();

    /**
     * Constructs a {@code JsonAdaptedMeme} with the given meme details.
     */
    @JsonCreator
    public JsonAdaptedLikeData(@JsonProperty("likes") Map<String, Integer> likeData) {

        if (likeData != null) {
            this.likeData.putAll(likeData);
        }
    }

    /**
     * Converts a given {@code LikeData} into this class for Json use.
     */
    public JsonAdaptedLikeData(LikeManager source) {
        likeData.putAll(source.getLikeDataInMap());
    }

    /**
     * Constructs an empty {@code JsonAdaptedLikeData}.
     */
    public JsonAdaptedLikeData() {}

    public void setJsonAdaptedLikeData(LikeData likeData) {
        this.likeData.putAll(likeData.getInMap());
    }

    /**
     * Converts this Json-friendly adapted meme object into the model's {@code Meme} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meme.
     */
    public LikeData toModelType() throws IllegalValueException {
        final LikeData likeData = new LikeData();
        likeData.setLikeMap(FXCollections.observableMap(this.likeData));

        return likeData;
    }

}
