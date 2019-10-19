package seedu.weme.logic.parser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.util.stream.Stream;

import seedu.weme.logic.commands.MemeExportCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.DirectoryPath;

/**
 * Parses input arguments and creates a new MemeExportCommand object
 */
public class MemeExportCommandParser implements Parser<MemeExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemeExportCommand
     * and returns an MemeExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MemeExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILEPATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeExportCommand.MESSAGE_USAGE));
        }

        DirectoryPath path = ParserUtil.parseDirectoryPath(argMultimap.getValue(PREFIX_FILEPATH).get());

        return new MemeExportCommand(path);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
