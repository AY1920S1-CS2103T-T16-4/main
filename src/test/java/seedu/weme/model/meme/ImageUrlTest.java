package seedu.weme.model.meme;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ImageUrlTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImageUrl(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidUrl = "";
        assertThrows(IllegalArgumentException.class, () -> new ImageUrl(invalidUrl));
    }

    @Test
    public void isValidUrl() {
        // null weme
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(ImageUrl.isValidUrl("")); // empty string
        assertFalse(ImageUrl.isValidUrl(" ")); // spaces only

        // valid addresses
        assertTrue(ImageUrl.isValidUrl("https://tinyurl.com/testWeme"));
    }
}
