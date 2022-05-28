package application.controllers;

import application.popups.AlertBox;
import application.popups.ConfirmBox;
import application.guihelpers.ListenerAdder;
import application.utility.TextFieldManager;
import application.utility.WordFileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the add words screen
 */
public class AddWordsController extends Controller implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private TextField wordTextField;
    @FXML
    private TextField definitionTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListenerAdder.setUpAddWordScreenListeners(wordTextField, definitionTextField, addButton);
    }

    /**
     * Method to handle what happens when info icon is clicked
     * @param event Button event
     */
    public void informationIcon(MouseEvent event) {
        AlertBox.newAlertBox("Adding Words",
                "You can add words here by filling out the two fields below\n" +
                        "or you can go to src/resources/words/My_Words.txt\n" +
                        "and add them manually, by typing the word followed by\n" +
                        "<SPACE> <COMMA> <SPACE> and then the definition");
    }

    /**
     * Method that checks if word input is valid, and adds it to the My_Words.txt file if it is.
     * @param event Button Event
     */
    public void addButton(ActionEvent event) {
        TextFieldManager textFieldManager = new TextFieldManager();
        WordFileManager wordFileManager = new WordFileManager();

        //Checks for letters in each text box
        if (textFieldManager.isNoLetters(wordTextField.getText()) || textFieldManager.isNoLetters(definitionTextField.getText())) {
            AlertBox.newAlertBox("Error", "Word and definition must both have at least one letter.");
            clearTextFields();
            return;
        }
        //Checks that word is not already added
        if (wordFileManager.doesWordExistInMyWords(wordTextField.getText())) {
            AlertBox.newAlertBox("Error", "Word is already added.");
            clearTextFields();
            return;
        }
        //Requires user to confirm
        if (!ConfirmBox.newConfirmBox("Confirm Add Word", "Are you sure you want to add this word.")) {
            return;
        }

        wordFileManager.addWordToMyWords(wordTextField.getText().trim(), definitionTextField.getText().trim());
        clearTextFields();
    }

    /**
     * clears textfields and move focus back to first text field
     */
    private void clearTextFields() {
        wordTextField.clear();
        definitionTextField.clear();
        wordTextField.requestFocus();
    }

}
