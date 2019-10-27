package seedu.weme.ui;

import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.weme.model.template.MemeCreation;

/**
 * Panel displaying the image of the current meme creation session.
 */
public class CreateImageDisplay extends UiPart<Region> {

    private static final String FXML = "CreateImageDisplay.fxml";

    @FXML
    private VBox memeCreationImageBox;
    @FXML
    private ImageView memeImage;

    private CreateImagePlaceholder placeholder = new CreateImagePlaceholder();

    public CreateImageDisplay(ObservableValue<MemeCreation> memeCreation) {
        super(FXML);
        updateImage(memeCreation.getValue());
        memeCreation.addListener(((observable, oldValue, newValue) -> updateImage(newValue)));
    }

    /**
     * Updates the image displayed.
     *
     * @param session the current meme creation session
     */
    private void updateImage(MemeCreation session) {
        if (session == null) {
            memeImage.setImage(null);
            memeCreationImageBox.getChildren().setAll(placeholder.getRoot());
        } else {
            memeImage.setImage(SwingFXUtils.toFXImage(session.getCurrentImage(), null));
            memeCreationImageBox.getChildren().setAll(memeImage);
        }
    }

}
