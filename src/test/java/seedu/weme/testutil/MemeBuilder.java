package seedu.weme.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.weme.model.meme.Description;
import seedu.weme.model.meme.ImageUrl;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.util.SampleDataUtil;

/**
 * A utility class to help with building Meme objects.
 */
public class MemeBuilder {

    public static final String DEFAULT_DESCRIPTION = "Meme created in CS2103 Lecture";
    public static final String DEFAULT_URL = "https://tinyurl.com/testWeme";

    private Description description;
    private ImageUrl url;
    private Set<Tag> tags;

    public MemeBuilder() {
        url = new ImageUrl(DEFAULT_URL);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MemeBuilder with the data of {@code memeToCopy}.
     */
    public MemeBuilder(Meme memeToCopy) {
        description = memeToCopy.getDescription();
        url = memeToCopy.getUrl();
        tags = new HashSet<>(memeToCopy.getTags());
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Meme} that we are building.
     */
    public MemeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Meme} that we are building.
     * @param description
     */
    public MemeBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code ImageUrl} of the {@code Meme} that we are building.
     */
    public MemeBuilder withUrl(String url) {
        this.url = new ImageUrl(url);
        return this;
    }

    public Meme build() {
        return new Meme(url, description, tags);
    }

}
