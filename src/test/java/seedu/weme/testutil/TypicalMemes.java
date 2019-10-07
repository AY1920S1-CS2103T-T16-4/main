package seedu.weme.testutil;

import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_URL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.weme.model.MemeBook;
import seedu.weme.model.meme.Meme;

/**
 * A utility class containing a list of {@code Meme} objects to be used in tests.
 */
public class TypicalMemes {

    public static final Meme ALICE = new MemeBuilder()
            .withUrl("https://tinyurl.com/testALICE")
            .withDescription("Description of Alice")
            .withTags("friends").build();
    public static final Meme BENSON = new MemeBuilder()
            .withUrl("https://tinyurl.com/testBENSON")
            .withDescription("Description of Benson")
            .withTags("owesMoney", "friends").build();
    public static final Meme CARL = new MemeBuilder()
            .withUrl("https://tinyurl.com/testCARL")
            .withDescription("Description of Carl").build();
    public static final Meme DANIEL = new MemeBuilder()
            .withUrl("https://tinyurl.com/testDANIEL")
            .withDescription("Description of Daniel").withTags("friends").build();
    public static final Meme ELLE = new MemeBuilder()
            .withUrl("https://tinyurl.com/testELLE")
            .withDescription("Description of Elle").build();
    public static final Meme FIONA = new MemeBuilder()
            .withUrl("https://tinyurl.com/testFIONA")
            .withDescription("Description of Fiona").build();
    public static final Meme GEORGE = new MemeBuilder()
            .withUrl("https://tinyurl.com/testGEORGE")
            .withDescription("Description of George").build();

    // Manually added
    public static final Meme HOON = new MemeBuilder()
            .withUrl("https://tinyurl.com/testHOON")
            .withDescription("Meme created in CS2103 Lecture").build();
    public static final Meme IDA = new MemeBuilder()
            .withUrl("https://tinyurl.com/testIDA")
            .withDescription("Meme created in CS2103 Lecture").build();

    // Manually added - Meme's details found in {@code CommandTestUtil}
    public static final Meme AMY = new MemeBuilder().withUrl(VALID_URL_AMY)
            .withDescription(VALID_DESCRIPTION_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Meme BOB = new MemeBuilder().withUrl(VALID_URL_BOB)
            .withDescription(VALID_DESCRIPTION_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMemes() {} // prevents instantiation

    /**
     * Returns an {@code MemeBook} with all the typical memes.
     */
    public static MemeBook getTypicalMemeBook() {
        MemeBook ab = new MemeBook();
        for (Meme meme : getTypicalMemes()) {
            ab.addMeme(meme);
        }
        return ab;
    }

    public static List<Meme> getTypicalMemes() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
