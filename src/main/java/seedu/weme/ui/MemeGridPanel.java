package seedu.weme.ui;

import java.util.logging.Logger;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.meme.Meme;


/**
 * Panel containing the list of memes.
 */
public class MemeGridPanel extends UiPart<Region> {
    private static final String FXML = "MemeGridPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MemeGridPanel.class);

    @FXML
    private GridView<Meme> memeGridView;

    private ObservableMap<String, Integer> likeData;

    public MemeGridPanel(ObservableList<Meme> memeList, ObservableMap<String, Integer> likeData) {
        super(FXML);
        memeGridView.setItems(memeList);
        memeGridView.setCellFactory(listView -> new MemeGridViewCell());
        this.likeData = likeData;
        likeData.addListener((MapChangeListener<String, Integer>) change -> memeGridView.autosize());
    }

    /**
     * Custom {@code GridCell} that displays the graphics of a {@code Meme} using a {@code MemeCard}.
     */
    class MemeGridViewCell extends GridCell<Meme> {
        @Override
        protected void updateItem(Meme meme, boolean empty) {
            super.updateItem(meme, empty);
            if (empty || meme == null) {
                setGraphic(null);
                setText(null);
            } else {
                String filePath = meme.getFilePath().toString();
                int likes = likeData.getOrDefault(filePath, 0);
                setGraphic(new MemeCard(meme, getIndex() + 1, likes).getRoot());
            }
        }
    }

}
