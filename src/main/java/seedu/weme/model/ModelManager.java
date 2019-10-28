package seedu.weme.model;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.util.CollectionUtil.requireAllNonNull;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.transformation.FilteredList;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.Template;
import seedu.weme.statistics.Stats;
import seedu.weme.statistics.TagWithCount;

/**
 * Represents the in-memory model of Weme data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedWeme versionedWeme;
    private final UserPrefs userPrefs;
    private final FilteredList<Meme> filteredMemes;
    private final FilteredList<Meme> filteredStagedMemeList;
    private final FilteredList<Meme> filteredImportMemeList;
    private final FilteredList<Template> filteredTemplates;

    // ModelContext determines which parser to use at any point of time.
    private SimpleObjectProperty<ModelContext> context = new SimpleObjectProperty<>(ModelContext.CONTEXT_MEMES);

    /**
     * Initializes a ModelManager with the given Weme and userPrefs.
     */
    public ModelManager(ReadOnlyWeme weme, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(weme, userPrefs);

        logger.fine("Initializing with Weme: " + weme + " and user prefs " + userPrefs);

        versionedWeme = new VersionedWeme(weme);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMemes = new FilteredList<>(versionedWeme.getMemeList());
        filteredStagedMemeList = new FilteredList<>(versionedWeme.getStagedMemeList());
        filteredImportMemeList = new FilteredList<>(versionedWeme.getImportList());
        filteredTemplates = new FilteredList<>(versionedWeme.getTemplateList());
    }

    public ModelManager() {
        this(new Weme(), new UserPrefs());
    }

    //=========== Export/Import ==============================================================================

    @Override
    public List<Path> getExportPathList() {
        return versionedWeme.getExportPathList();
    }

    @Override
    public void importMemes() throws IOException {
        versionedWeme.importMeme(getMemeImagePath());
        versionedWeme.clearImportList();
    }

    @Override
    public void loadMemes(List<Path> pathList) {
        versionedWeme.loadMemes(pathList);
    }

    @Override
    public void clearExportList() {
        versionedWeme.clearExportList();
    }


    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public ObservableMap<String, String> getObservableUserPreferences() {
        return userPrefs.getObservableUserPreferences();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getDataFilePath() {
        return userPrefs.getDataFilePath();
    }

    @Override
    public void setDataFilePath(Path dataFilePath) {
        requireNonNull(dataFilePath);
        userPrefs.setDataFilePath(dataFilePath);
    }

    @Override
    public Path getMemeImagePath() {
        return userPrefs.getMemeImagePath();
    }

    @Override
    public void setMemeImagePath(Path memeImagePath) {
        requireNonNull(memeImagePath);
        userPrefs.setMemeImagePath(memeImagePath);
    }

    @Override
    public Path getTemplateImagePath() {
        return userPrefs.getTemplateImagePath();
    }

    @Override
    public void setTemplateImagePath(Path templateImagePath) {
        requireNonNull(templateImagePath);
        userPrefs.setTemplateImagePath(templateImagePath);
    }

    //=========== Weme ================================================================================

    @Override
    public void setWeme(ReadOnlyWeme weme) {
        this.versionedWeme.resetData(weme);
    }

    @Override
    public ReadOnlyWeme getWeme() {
        return versionedWeme;
    }

    @Override
    public void clearMemes() {
        versionedWeme.setMemes(new ArrayList<>());
    }

    @Override
    public boolean hasMeme(Meme meme) {
        requireNonNull(meme);
        return versionedWeme.hasMeme(meme);
    }

    @Override
    public void deleteMeme(Meme target) {
        versionedWeme.removeMeme(target);
    }

    @Override
    public void addMeme(Meme meme) {
        versionedWeme.addMeme(meme);
        updateFilteredMemeList(PREDICATE_SHOW_ALL_MEMES);
    }

    @Override
    public void setMeme(Meme target, Meme editedMeme) {
        requireAllNonNull(target, editedMeme);

        versionedWeme.setMeme(target, editedMeme);
    }

    @Override
    public void stageMeme(Meme meme) {
        versionedWeme.stageMeme(meme);
    }

    @Override
    public void unstageMeme(Meme meme) {
        versionedWeme.unstageMeme(meme);
    }

    @Override
    public boolean hasTemplate(Template template) {
        requireNonNull(template);
        return versionedWeme.hasTemplate(template);
    }

    @Override
    public void deleteTemplate(Template template) {
        versionedWeme.removeTemplate(template);
    }

    @Override
    public void addTemplate(Template template) {
        versionedWeme.addTemplate(template);
        updateFilteredTemplateList(PREDICATE_SHOW_ALL_TEMPLATES);
    }

    @Override
    public void setTemplate(Template target, Template editedTemplate) {
        requireAllNonNull(target, editedTemplate);
        versionedWeme.setTemplate(target, editedTemplate);
    }

    //=========== Filtered Meme/Template List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Meme} backed by the internal list of
     * {@code versionedWeme}
     */
    @Override
    public ObservableList<Meme> getFilteredMemeList() {
        return filteredMemes;
    }

    @Override
    public ObservableList<Meme> getFilteredStagedMemeList() {
        return filteredStagedMemeList;
    }

    @Override
    public ObservableList<Meme> getFilteredImportList() {
        return filteredImportMemeList;
    }

    @Override
    public void updateFilteredMemeList(Predicate<Meme> predicate) {
        requireNonNull(predicate);
        filteredMemes.setPredicate(predicate);
    }

    @Override
    public ObservableList<Template> getFilteredTemplateList() {
        return filteredTemplates;
    }

    @Override
    public void updateFilteredTemplateList(Predicate<Template> predicate) {
        requireNonNull(predicate);
        filteredTemplates.setPredicate(predicate);
    }

    @Override
    public void setContext(ModelContext context) {
        this.context.setValue(context);
    }

    @Override
    public ObservableValue<ModelContext> getContext() {
        return context;
    }

    @Override
    public boolean canUndoWeme() {
        return versionedWeme.canUndo();
    }

    @Override
    public boolean canRedoWeme() {
        return versionedWeme.canRedo();
    }

    @Override
    public void undoWeme() {
        versionedWeme.undo();
    }

    @Override
    public void redoWeme() {
        versionedWeme.redo();
    }

    @Override
    public void commitWeme() {
        versionedWeme.commit();
    }

    //=========== Statistics Methods =============================================================

    @Override
    public Stats getStats() {
        return versionedWeme.getStats();
    }

    @Override
    public int getLikesByMeme(Meme meme) {
        return versionedWeme.getLikesByMeme(meme);
    }

    @Override
    public ObservableMap<String, Integer> getObservableLikeData() {
        return versionedWeme.getObservableLikeData();
    }

    @Override
    public void incrementMemeLikeCount(Meme meme) {
        versionedWeme.incrementMemeLikeCount(meme);
    }

    @Override
    public int getCountOfTag(Tag tag) {
        return versionedWeme.getCountOfTag(tag);
    }

    @Override
    public List<TagWithCount> getTagsWithCountList() {
        return versionedWeme.getTagsWithCountList();
    };

    @Override
    public void clearMemeStats(Meme meme) {
        versionedWeme.clearMemeStats(meme);
    }

    //=========== Records method ================================================================================

    @Override
    public Records getRecords() {
        return versionedWeme.getRecords();
    }

    @Override
    public Set<String> getPathRecords() {
        return versionedWeme.getPaths();
    }

    @Override
    public Set<String> getDescriptionRecords() {
        return versionedWeme.getDescriptions();
    }

    @Override
    public Set<String> getTagRecords() {
        return versionedWeme.getTags();
    }

    @Override
    public Set<String> getNameRecords() {
        return versionedWeme.getNames();
    }

    @Override
    public void addMemeToRecord(Meme meme) {
        versionedWeme.addPath(meme.getImagePath());
        versionedWeme.addDescription(meme.getDescription());
        versionedWeme.addTags(meme.getTags());
    }

    @Override
    public void cleanMemeStorage() {
        try {
            Set<File> filesToKeep = new HashSet<>();
            for (Meme meme : versionedWeme.getMemeList()) {
                File file = meme.getImagePath().getFilePath().toFile();
                filesToKeep.add(file);
            }

            Files.list(getMemeImagePath())
                    .map(Path::toFile)
                    .filter(file -> !filesToKeep.contains(file))
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void cleanTemplateStorage() {
        try {
            Set<File> filesToKeep = new HashSet<>();
            for (Template template : versionedWeme.getTemplateList()) {
                File file = template.getImagePath().getFilePath().toFile();
                filesToKeep.add(file);
            }

            Files.list(getTemplateImagePath())
                    .map(Path::toFile)
                    .filter(file -> !filesToKeep.contains(file))
                    .forEach(File::delete);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedWeme.equals(other.versionedWeme)
                && userPrefs.equals(other.userPrefs)
                && filteredMemes.equals(other.filteredMemes)
                && context.getValue().equals(other.context.getValue());
    }

}
