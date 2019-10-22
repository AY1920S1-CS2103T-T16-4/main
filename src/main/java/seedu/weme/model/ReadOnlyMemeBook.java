package seedu.weme.model;

import javafx.collections.ObservableList;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;

/**
 * Unmodifiable view of an meme book
 */
public interface ReadOnlyMemeBook {

    /**
     * Returns an unmodifiable view of the memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getMemeList();

    /**
     * Returns an umodifiable view of the staged memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getStagedMemeList();

    /**
     * Returns an umodifiable view of the import memes list.
     * This list will not contain any duplicate memes.
     */
    ObservableList<Meme> getImportList();

    /**
     * Returns an unmodifiable view of the template list.
     * This list will not contain any duplicate templates.
     * @return
     */
    ObservableList<Template> getTemplateList();

}
