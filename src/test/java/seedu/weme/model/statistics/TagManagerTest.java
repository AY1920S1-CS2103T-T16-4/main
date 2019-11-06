package seedu.weme.model.statistics;

import org.junit.jupiter.api.Test;
import seedu.weme.model.Weme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.MemeBuilder;
import seedu.weme.testutil.WemeBuilder;

import static org.junit.jupiter.api.Assertions.*;

class TagManagerTest {
    private TagManager tagManager = new TagManager();
    private TagManager expectedTagManager = new TagManager();

    @Test
    public void testCountOfTag() {
        Tag tag = new Tag("charmander");
        Meme memeWithOneTag = new MemeBuilder()
                .withFilePath("src/test/data/memes/charmander_meme.jpg")
                .withDescription("A meme about Char and charmander.")
                .withTags("charmander").build();
        Weme weme = new WemeBuilder()
                .withMeme(memeWithOneTag)
                .build();

        assertEquals(tagManager.getCountOfTag(weme.getMemeList(), tag), 1);
    }
}