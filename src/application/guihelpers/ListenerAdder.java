package application.guihelpers;

import application.launch.Main;
import application.popups.ConfirmBox;
import application.quizlogic.Quiz;
import application.utility.TextFieldManager;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ListenerAdder {

    /**
     * Method to add desired listeners to the text fields and exit button on the game quiz screen
     * @param textField TextField to add listeners to
     * @param button Button to trigger on enter
     * @param quiz Quiz to set close request on
     */
    public static void setUpGameQuizScreenListeners(TextField textField, Button button, Quiz quiz) {
        linkButtonToTextBoxOnEnter(textField, button);
        addListenerForTextFieldEntry(textField);
        setCloseRequestAfterQuiz(quiz);
    }

    /**
     * Method to add desired listeners to the text fields and exit button on the practise quiz screen
     * @param textField TextField to add listeners to
     * @param submitButton Button to trigger on enter
     * @param nextWordButton Button to trigger on enter
     * @param quiz Quiz to set close request on
     */
    public static void setUpPractiseQuizScreenListeners(TextField textField, Button submitButton, Button nextWordButton, Quiz quiz) {
        linkButtonToTextBoxOnEnterForPractiseQuiz(textField, submitButton, nextWordButton);
        addListenerForTextFieldEntry(textField);
        setCloseRequestAfterQuiz(quiz);
    }

    /**
     * Method to add desired listeners to the text fields on the add words screen
     * @param wordTextField TextField to add listeners to
     * @param definitionTextField TextField to add listeners to
     * @param addButton Button to trigger on enter
     */
    public static void setUpAddWordScreenListeners(TextField wordTextField, TextField definitionTextField, Button addButton) {
        addListenerForTextFieldEntry(wordTextField);
        addListenerForTextFieldEntry(definitionTextField);
        linkButtonToTextBoxOnEnter(wordTextField, addButton);
        linkButtonToTextBoxOnEnter(definitionTextField, addButton);
    }

    /**
     * Method to add the two most commonly desired listeners to a text field
     * @param textField TextField to add listeners to
     * @param button Button to trigger on enter
     */
    public static void setUpBasicTextFieldListeners(TextField textField, Button button) {
        addListenerForTextFieldEntry(textField);
        linkButtonToTextBoxOnEnter(textField, button);
    }

    /**
     * Method to make it so if the user hits enter after typing a word
     * in the specified text field it will hit the specified button
     * @param textField TextField to add listener to
     * @param button Button to fire
     */
    public static void linkButtonToTextBoxOnEnter(TextField textField, Button button) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                button.fire();
                ev.consume();
            }
        });
    }

    /**
     * Method to make it so if the user hits enter after typing a word it will submit or next
     * @param textField TextField to add listener to
     * @param submitButton Button to fire if visible
     * @param nextButton Button to fire if visible
     */
    public static void linkButtonToTextBoxOnEnterForPractiseQuiz(TextField textField, Button submitButton, Button nextButton) {
        textField.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                if (submitButton.isVisible()) {
                    submitButton.fire();
                    ev.consume();
                } else if (nextButton.isVisible()) {
                    nextButton.fire();
                    ev.consume();
                }
            }
        });
    }

    /**
     * Method to prevent user from entering illegal characters and allow the macronising of vowels
     * @param textField TextField to add the listener to
     */
    public static void addListenerForTextFieldEntry(TextField textField) {
        TextFieldManager textFieldManager = new TextFieldManager();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            int indexToMacronise = textFieldManager.checkForMacronise(oldValue, newValue);
            if (indexToMacronise != -1) {
                textField.setText(textFieldManager.macroniseCharacter(indexToMacronise, oldValue));
            } else if (!textFieldManager.isLegalCharacter(newValue)) {
                textField.setText(oldValue);
            }
        });
    }

    /**
     * Method to make it so that when the window is closed a confirmation window will appear
     * and if festival is running it will be terminated
     * @param quiz Quiz in which to terminate reading
     */
    private static void setCloseRequestAfterQuiz(Quiz quiz) {
        Main.window.setOnCloseRequest(windowEvent -> {
            if(!ConfirmBox.newConfirmBox("Confirm Quit", "Are you sure you want to quit")) {
                windowEvent.consume();
                return;
            }
            if (quiz.getBackgroundWordRead().isRunning()) {
                quiz.getBackgroundWordRead().stopReading();
            }
        });
    }

    /**
     * Method to make it so that when the window is closed a confirmation window will appear
     */
    public static void setCloseRequestBeforeQuiz() {
        Main.window.setOnCloseRequest(windowEvent -> {
            if(!ConfirmBox.newConfirmBox("Confirm Quit", "Are you sure you want to quit")) {
                windowEvent.consume();
            }
        });
    }
}
