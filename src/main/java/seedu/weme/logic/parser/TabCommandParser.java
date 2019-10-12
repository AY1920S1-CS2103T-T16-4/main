package seedu.weme.logic.parser;

import static seedu.weme.model.ModelContext.CONTEXT_MEMES;

import seedu.weme.logic.commands.TabCommand;
import seedu.weme.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TabCommand object.
 */
public class TabCommandParser implements Parser<TabCommand> {

    public static final String MESSAGE_INVALID_CONTEXT = "Tab provided is not a valid tab.";

    /**
     * Parses the given {@code String} of arguments in the context of the TabCommand
     * and returns a TabCommand object for execution.
     * @throws ParseException if the user input does not provide a valid context.
     */
    @Override
    public TabCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.equals(CONTEXT_MEMES.getContextName())) {
            return new TabCommand(CONTEXT_MEMES);
        }
        throw new ParseException(MESSAGE_INVALID_CONTEXT);
    }
}
