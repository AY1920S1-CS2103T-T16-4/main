package seedu.weme.logic.commands.templatecommand;

import static java.util.Objects.requireNonNull;

import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.model.Model;

/**
 * Lists all archived memes in Weme to the user.
 */
public class TemplateArchivesCommand extends Command {

    public static final String COMMAND_WORD = "archives";

    public static final String MESSAGE_SUCCESS = "Listed all archived templates";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMemeList(template -> template.isArchived());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
