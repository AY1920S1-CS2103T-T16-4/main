package seedu.weme.ui;

import java.util.regex.Matcher;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.contextparser.WemeParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.prompter.exceptions.PromptException;

import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private final CommandPrompter commandPrompter;

    private boolean isShowingCommandSuccess = false;

    @FXML
    private TextField commandTextField;

    public CommandBox(CommandExecutor commandExecutor, CommandPrompter commandPrompter) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.commandPrompter = commandPrompter;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> {
            setStyleToDefault();
            if (!isShowingCommandSuccess) {
                displayCommandPrompt();
            } else {
                isShowingCommandSuccess = false;
            }
        });
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            keyEvent.consume();
            final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(commandTextField.getText().trim());
            if (matcher.matches()) {
                try {
                    final String commandWord = matcher.group(WemeParser.COMMAND_WORD);
                    final String arguments = matcher.group(ARGUMENTS);
                    System.out.println(commandWord + arguments);
                    commandExecutor.execute(commandTextField.getText());
                } catch (CommandException | ParseException e) {
                    setStyleToIndicateCommandFailure();
                }
            }
            break;
        case DOWN:
            keyEvent.consume();
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            isShowingCommandSuccess = true;
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Display the command prompt in the result box.
     */
    private void displayCommandPrompt() {
        try {
            commandPrompter.execute(commandTextField.getText());
        } catch (PromptException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.weme.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    /**
     * Represents a function that can auto-prompt commands.
     */
    @FunctionalInterface
    public interface CommandPrompter {
        /**
         * Parse the user input and display the suggestions in ResultDisplay.
         * @param userInput text input from CommandBox
         */
        void execute(String userInput) throws PromptException;
    }

}
