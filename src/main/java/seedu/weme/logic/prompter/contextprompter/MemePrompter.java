package seedu.weme.logic.prompter.contextprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.contextparser.WemeParser.COMMAND_WORD;
import static seedu.weme.logic.prompter.util.PrompterUtil.GENERAL_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.MEME_COMMANDS;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptCommandWord;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.memecommand.MemeAddCommand;
import seedu.weme.logic.commands.memecommand.MemeClearCommand;
import seedu.weme.logic.commands.memecommand.MemeDeleteCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.commands.memecommand.MemeFindCommand;
import seedu.weme.logic.commands.memecommand.MemeLikeCommand;
import seedu.weme.logic.commands.memecommand.MemeListCommand;
import seedu.weme.logic.commands.memecommand.MemeStageCommand;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeAddCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeDeleteCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeEditCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeFindCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeLikeCommandPrompter;
import seedu.weme.logic.prompter.commandprompter.memecommandprompter.MemeStageCommandPrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt command arguments in the meme context.
 */
public class MemePrompter extends WemePrompter {

    @Override
    public CommandPrompt promptCommand(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new CommandPrompt(MEME_COMMANDS.stream().sorted().reduce((x, y) -> x + '\n' + y).orElse(""),
                    MEME_COMMANDS.stream().sorted().findFirst().orElse(""));
        }

        final String commandWord = matcher.group(COMMAND_WORD);
        final String arguments = matcher.group(ARGUMENTS);
        if (GENERAL_COMMANDS.contains(commandWord)) {
            return super.promptCommand(model, userInput);
        }

        switch (commandWord) {
        case MemeAddCommand.COMMAND_WORD:
            return new MemeAddCommandPrompter().prompt(model, userInput);

        case MemeEditCommand.COMMAND_WORD:
            return new MemeEditCommandPrompter().prompt(model, userInput);

        case MemeDeleteCommand.COMMAND_WORD:
            return new MemeDeleteCommandPrompter().prompt(model, userInput);

        case MemeFindCommand.COMMAND_WORD:
            return new MemeFindCommandPrompter().prompt(model, userInput);

        case MemeLikeCommand.COMMAND_WORD:
            return new MemeLikeCommandPrompter().prompt(model, userInput);

        case MemeStageCommand.COMMAND_WORD:
            return new MemeStageCommandPrompter().prompt(model, userInput);

        case MemeClearCommand.COMMAND_WORD:
        case MemeListCommand.COMMAND_WORD:
            return new CommandPrompt(userInput);

        default:
            if (arguments.isBlank()) {
                return promptCommandWord(MEME_COMMANDS, commandWord);
            } else {
                throw new PromptException(MESSAGE_UNKNOWN_COMMAND);
            }
        }
    }
}
