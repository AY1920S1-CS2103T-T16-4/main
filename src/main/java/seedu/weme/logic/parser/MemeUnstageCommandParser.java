package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.UnstageCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MemeUnstageCommandParser object.
 */
public class MemeUnstageCommandParser implements Parser<UnstageCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MemeParseCommand
     * and returns a MemeParseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnstageCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnstageCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnstageCommand.MESSAGE_USAGE), pe);
        }
    }

}
