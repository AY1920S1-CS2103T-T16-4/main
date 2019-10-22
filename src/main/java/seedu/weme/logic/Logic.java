package seedu.weme.logic;

import java.nio.file.Path;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.ModelContext;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.Template;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the MemeBook.
     *
     * @see seedu.weme.model.Model#getMemeBook()
     */
    ReadOnlyMemeBook getMemeBook();

    /** Returns an unmodifiable view of the filtered list of memes */
    ObservableList<Meme> getFilteredMemeList();

    /** Returns an unmodifiable view of the filtered list of memes */
    ObservableList<Meme> getImportList();

    /** Returns an unmodifiable view of the filtered staged list of memes */
    ObservableList<Meme> getFilteredStagedMemeList();

    /** Returns an unmodifiable view of the filtered list of templates */
    ObservableList<Template> getFilteredTemplateList();

    /** Returns the current context */
    SimpleObjectProperty<ModelContext> getContext();

    /**
     * Returns the user prefs' meme book file path.
     */
    Path getMemeBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Handles any logic that needs to be done before exiting weme.
     */
    void cleanUp();
}
