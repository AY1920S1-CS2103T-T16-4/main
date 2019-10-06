package seedu.weme.model.util;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.weme.model.MemeBook;
import seedu.weme.model.ReadOnlyMemeBook;
import seedu.weme.model.meme.Address;
import seedu.weme.model.meme.ImageUrl;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.Name;
import seedu.weme.model.tag.Tag;

/**
 * Contains utility methods for populating {@code MemeBook} with sample data.
 */
public class SampleDataUtil {
    public static Meme[] getSampleMemes() {
        return new Meme[]{
                new Meme(new Name("Alex Yeoh"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        new ImageUrl("https://tinyurl.com/testWeme"),
                        getTagSet("friends")),
                new Meme(new Name("Bernice Yu"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new ImageUrl("https://tinyurl.com/testWeme"),
                        getTagSet("colleagues", "friends")),
                new Meme(new Name("Charlotte Oliveiro"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new ImageUrl("https://tinyurl.com/testWeme"),
                        getTagSet("neighbours")),
                new Meme(new Name("David Li"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new ImageUrl("https://tinyurl.com/testWeme"),
                        getTagSet("family")),
                new Meme(new Name("Irfan Ibrahim"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        new ImageUrl("https://tinyurl.com/testWeme"),
                        getTagSet("classmates")),
                new Meme(new Name("Roy Balakrishnan"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        new ImageUrl("https://tinyurl.com/testWeme"),
                        getTagSet("colleagues"))
        };
    }

    public static ReadOnlyMemeBook getSampleMemeBook() {
        MemeBook sampleMb = new MemeBook();
        for (Meme sampleMeme : getSampleMemes()) {
            sampleMb.addMeme(sampleMeme);
        }
        return sampleMb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
