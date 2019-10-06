package seedu.weme.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.weme.storage.JsonAdaptedMeme.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.TypicalMemes.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.meme.Address;
import seedu.weme.model.meme.ImageUrl;
import seedu.weme.model.meme.Name;

public class JsonAdaptedMemeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_URL = "http//tinyurl.com/testWeme";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_URL = BENSON.getUrl().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validMemeDetails_returnsMeme() throws Exception {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(BENSON);
        assertEquals(BENSON, meme.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedMeme meme =
                new JsonAdaptedMeme(INVALID_NAME, VALID_ADDRESS, VALID_URL, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(null, VALID_ADDRESS, VALID_URL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedMeme meme =
                new JsonAdaptedMeme(VALID_NAME, INVALID_ADDRESS, VALID_URL, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(VALID_NAME, null, VALID_URL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }
    @Test

    public void toModelType_invalidUrl_throwsIllegalValueException() {
        JsonAdaptedMeme meme =
                new JsonAdaptedMeme(VALID_NAME, VALID_ADDRESS, INVALID_URL, VALID_TAGS);
        String expectedMessage = ImageUrl.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }

    @Test
    public void toModelType_nullUrl_throwsIllegalValueException() {
        JsonAdaptedMeme meme = new JsonAdaptedMeme(VALID_NAME, VALID_ADDRESS, null,VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meme::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedMeme meme =
                new JsonAdaptedMeme(VALID_NAME, VALID_ADDRESS, VALID_URL, invalidTags);
        assertThrows(IllegalValueException.class, meme::toModelType);
    }

}
