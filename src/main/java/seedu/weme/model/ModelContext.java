package seedu.weme.model;

import seedu.weme.logic.parser.MemeBookMemeParser;
import seedu.weme.logic.parser.MemeBookParser;

/**
 * Determines the current context of the application.
 */
public enum ModelContext {
    // List of all contexts in the application.
    CONTEXT_MEMES(new MemeBookMemeParser());

    private MemeBookParser parser;

    /**
     * Context determines which parser to use. The parser determines which commands are available at any point of time.
     * @param parser Parser to use to parse commands in given context.
     */
    ModelContext(MemeBookParser parser) {
        this.parser = parser;
    }

    /**
     * Returns the parser of the current context.
     * @return Parser of current context
     */
    public MemeBookParser getParser() {
        return this.parser;
    }
}
