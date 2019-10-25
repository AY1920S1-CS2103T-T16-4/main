package seedu.weme.model;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;
import seedu.weme.statistics.Stats;
import seedu.weme.statistics.TagWithCount;
import seedu.weme.statistics.TagWithLike;

/**
 * Unmodifiable view of an weme
 */
public interface ReadOnlyWeme {

    /**
     * Returns an unmodifiable view of the memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getMemeList();

    /**
     * Returns an unmodifiable view of the template list.
     * This list will not contain any duplicate templates.
     */
    ObservableList<Template> getTemplateList();

    /**
     * Returns the statistics data of Weme.
     */
    Stats getStats();

    /**
     * Returns a list of tags with their counts.
     */
    List<TagWithCount> getTagsWithCountList();

    /**
     * Returns a list of tags with their like counts.
     */
    List<TagWithLike> getTagsWithLikeCountList();
}
