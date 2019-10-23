package seedu.weme.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;
import seedu.weme.statistics.LikeData;
import seedu.weme.statistics.Stats;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Meme> PREDICATE_SHOW_ALL_MEMES = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Template> PREDICATE_SHOW_ALL_TEMPLATES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' data file path.
     */
    Path getDataFilePath();

    /**
     * Sets the user prefs' data file path.
     */
    void setDataFilePath(Path dataFilePath);

    /**
     * Returns the user prefs' meme image path.
     */
    Path getMemeImagePath();

    /**
     * Sets the user prefs' meme image path.
     */
    void setMemeImagePath(Path memeImagePath);

    /**
     * Returns the user prefs' template image path.
     */
    Path getTemplateImagePath();

    /**
     * Sets the user prefs' template image path.
     */
    void setTemplateImagePath(Path templateImagePath);

    /**
     * Replaces meme book data with the data in {@code memeBook}.
     */
    void setMemeBook(ReadOnlyMemeBook memeBook);

    /**
     * Returns the MemeBook
     */
    ReadOnlyMemeBook getMemeBook();

    /**
     * Returns true if a meme with the same identity as {@code meme} exists in the meme book.
     */
    boolean hasMeme(Meme meme);

    /**
     * Stages the given meme for export.
     * The meme must exist in the meme book.
     */
    void stageMeme(Meme meme);

    /**
     * Unstages the given meme for export.
     * The meme must exist in the meme book.
     */
    void unstageMeme(Meme meme);

    /**
     * Deletes the given meme.
     * The meme must exist in the meme book.
     */
    void deleteMeme(Meme target);

    /**
     * Adds the given meme.
     * {@code meme} must not already exist in the meme book.
     */
    void addMeme(Meme meme);

    /**
     * Exports the meme to given directory
     */
    void exportMeme(DirectoryPath exportPath) throws IOException;

    /**
     * Imports the meme to storage
     */
    void importMeme() throws IOException;

    /**
     * Loads the memes to import staging area.
     */
    void loadMeme(DirectoryPath directoryPath) throws IOException;

    /**
     * Replaces the given meme {@code target} with {@code editedMeme}.
     * {@code target} must exist in the meme book.
     * The meme identity of {@code editedMeme} must not be the same as another existing meme in the meme book.
     */
    void setMeme(Meme target, Meme editedMeme);

    /** Returns an unmodifiable view of the filtered meme list */
    ObservableList<Meme> getFilteredMemeList();

    /**
     * Returns an unmodifiable view of the staged meme list.
     */
    ObservableList<Meme> getFilteredStagedMemeList();

    /**
     * Returns an unmodifiable view of the import meme list.
     */
    ObservableList<Meme> getImportList();

    /**
     * Updates the filter of the filtered meme list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemeList(Predicate<Meme> predicate);

    /** Returns an unmodifiable view of the filtered template list */
    ObservableList<Template> getFilteredTemplateList();

    /**
     * Updates the filter of the filtered template list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTemplateList(Predicate<Template> predicate);

    /**
     * Returns the context of the model.
     */
    SimpleObjectProperty<ModelContext> getContext();

    /**
     * Returns true if model has a previous state to restore.
     */
    boolean canUndoMemeBook();

    /**
     * Returns true if model has a undone state to restore.
     */
    boolean canRedoMemeBook();

    /**
     * Restores the model's meme book to its previous state.
     */
    void undoMemeBook();

    /**
     * Restores the mode's meme book to its previously undone state.
     */
    void redoMemeBook();

    /**
     * Saves the current meme book state for undo/redo.
     */
    void commitMemeBook();

    /**
     * Returns statistics data.
     */
    Stats getStats();

    /**
     * Returns the like data.
     */
    LikeData getLikeData();

    ObservableMap<String, Integer> getObservableLikeData();

    /**
     * Increments likes of a meme by the Meme object.
     */
    void incrementMemeLikeCount(Meme meme);

    /**
     * Deletes stats data by meme.
     */
    void clearMemeStats(Meme memeToDelete);


    /**
     * Clears the image data folder of any memes that are not referenced in weme.
     */
    void cleanMemeStorage();
}
