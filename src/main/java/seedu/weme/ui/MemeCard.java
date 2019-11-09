package seedu.weme.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Meme}.
 */
public class MemeCard extends UiPart<Region> {

    private static final String FXML = "MemeGridCard.fxml";
    private static final double TAGS_HEIGHT = 20.0;
    private static final int TAGS_GAP_BY_CHAR = 2;

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
    private HBox likeBox;
    @FXML
    private Label likes;
    @FXML
    private Label dislikes;

    public MemeCard(Meme meme,
                    int displayedIndex,
                    SimpleIntegerProperty numOfLikes,
                    SimpleIntegerProperty numOfDislikes) {
        super(FXML);
        this.meme = meme;
        id.setText(displayedIndex + "");
        Image image = new Image(meme.getImagePath().toUrl().toString(), 200, 200, true, true, true);
        Image imageCopy = new Image(meme.getImagePath().toUrl().toString());
        double height = imageCopy.getHeight();
        double width = imageCopy.getWidth();
        double aspectRatio = height / width;
        double imageHeight = height > width ? 200 : aspectRatio * 200;
        int rowsForTags = 1 + (int) Math.round(Math.floor((200 - imageHeight) / 20));
        List<Integer> lengths = meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .map(tag -> tag.getTagName().length())
                .collect(Collectors.toCollection(ArrayList::new));
        int limit = 0;
        int numOfCharForTags = rowsForTags * 25;
        int numOfCharInTagPane = 0;
        for (int i = 0; i < meme.getTags().size(); i++) {
            numOfCharInTagPane += lengths.get(i) + 2;
            limit++;
            if (numOfCharInTagPane > numOfCharForTags) {
                break;
            }
        }
        display.setImage(image);
        description.setText(meme.getDescription().value);
        tags.orientationProperty().setValue(Orientation.HORIZONTAL);
        tags.setMaxHeight(25 * rowsForTags );
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .limit(limit)
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        likes.setText(" " + numOfLikes.get() + " ");
        dislikes.setText(" " + numOfDislikes.get() + " ");
        numOfLikes.addListener((observable, oldValue, newValue) ->
                likes.setText(Integer.toString((int) newValue)));
        numOfDislikes.addListener((observable, oldValue, newValue) ->
                dislikes.setText(Integer.toString((int) newValue)));
    }

    /**
     * Updates the card content except for the meme image.
     *
     * @param meme     the meme this card is for
     * @param newIndex the new index of the this card
     */
    public void update(Meme meme, int newIndex, SimpleIntegerProperty numOfLikes, SimpleIntegerProperty numOfDislikes) {
        id.setText(newIndex + "");
        description.setText(meme.getDescription().value);
        tags.getChildren().clear();
        Image imageCopy = new Image(meme.getImagePath().toUrl().toString());
        double height = imageCopy.getHeight();
        double width = imageCopy.getWidth();
        double aspectRatio = height / width;
        double imageHeight = height > width ? 200 : aspectRatio * 200;
        int rowsForTags = 1 + (int) Math.round(Math.floor((200 - imageHeight) / 20));
        List<Integer> lengths = meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .map(tag -> tag.getTagName().length())
                .collect(Collectors.toCollection(ArrayList::new));
        int limit = 0;
        int numOfCharForTags = rowsForTags * 25;
        int numOfCharInTagPane = 0;
        for (int i = 0; i < meme.getTags().size(); i++) {
            numOfCharInTagPane += lengths.get(i) + 2;
            limit++;
            if (numOfCharInTagPane > numOfCharForTags) {
                break;
            }
        }
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .limit(limit)
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (limit < meme.getTags().size()) {
            tags.getChildren().add(new Label(".."));
        }
        likes.setText(" " + numOfLikes.get() + " ");
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
                && meme.equals(card.meme)
                && likes.equals(card.likes)
                && dislikes.equals(card.dislikes);
    }
}
