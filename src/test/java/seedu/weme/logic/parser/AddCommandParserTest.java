package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.weme.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.weme.logic.commands.CommandTestUtil.URL_DESC_AMY;
import static seedu.weme.logic.commands.CommandTestUtil.URL_DESC_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.weme.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.weme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.weme.testutil.TypicalMemes.AMY;
import static seedu.weme.testutil.TypicalMemes.BOB;

import org.junit.jupiter.api.Test;

import seedu.weme.logic.commands.AddCommand;
import seedu.weme.model.meme.ImageUrl;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.tag.Tag;
import seedu.weme.testutil.MemeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Meme expectedMeme = new MemeBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, URL_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedMeme));

        // multiple addresses - last weme accepted
        assertParseSuccess(parser, URL_DESC_BOB + DESCRIPTION_DESC_AMY
                + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMeme));

        // multiple tags - all accepted
        Meme expectedMemeMultipleTags = new MemeBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, URL_DESC_BOB + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedMemeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Meme expectedMeme = new MemeBuilder(AMY).withTags().build();
        assertParseSuccess(parser, URL_DESC_AMY + DESCRIPTION_DESC_AMY,
                new AddCommand(expectedMeme));
        // Empty Description
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_URL_BOB + DESCRIPTION_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_URL_BOB + VALID_DESCRIPTION_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid weme
        assertParseFailure(parser, INVALID_URL_DESC + DESCRIPTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, ImageUrl.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, URL_DESC_BOB + DESCRIPTION_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + URL_DESC_BOB
                + DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
