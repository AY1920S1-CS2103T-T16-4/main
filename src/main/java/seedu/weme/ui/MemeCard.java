package seedu.weme.ui;

import java.io.File;
import java.util.Comparator;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.weme.model.meme.Meme;

/**
 * An UI component that displays information of a {@code Meme}.
 */
public class MemeCard extends UiPart<Region> {

    private static final String FXML = "MemeGridCard.fxml";
    private static final String LIKE_ICON = "src/main/resources/images/like_icon.png";
    private static final String DISLIKE_ICON = "src/main/resources/images/dislike_icon.png";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Weme level 4</a>
     */

    public final Meme meme;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView display;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView likeIcon;
    @FXML
    private Label likes;
    @FXML
    private ImageView dislikeIcon;
    @FXML
    private Label dislikes;

    public MemeCard(Meme meme,
                    int displayedIndex,
                    SimpleIntegerProperty numOfLikes,
                    SimpleIntegerProperty numOfDislikes) {
        super(FXML);
        this.meme = meme;
        id.setText(displayedIndex + "");
        display.setImage(new Image(meme.getImagePath().toUrl().toString()));
        description.setText(meme.getDescription().value);
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        likeIcon.setImage(new Image((new File(LIKE_ICON)).toURI().toString()));
        likes.setText(" " + numOfLikes.get() + " ");
        dislikeIcon.setImage(new Image((new File(DISLIKE_ICON)).toURI().toString()));
        dislikes.setText(" " + numOfDislikes.get() + " ");
        numOfLikes.addListener((observable, oldValue, newValue) ->
                likes.setText(Integer.toString((int) newValue)));
        numOfDislikes.addListener((observable, oldValue, newValue) ->
                dislikes.setText(Integer.toString((int) newValue)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemeCard)) {
            return false;
        }

        // state check
        MemeCard card = (MemeCard) other;
        return id.getText().equals(card.id.getText())
                && meme.equals(card.meme);
    }
}
