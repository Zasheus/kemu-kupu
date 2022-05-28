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
 * Controller class for the remove words screen
 */
public class RemoveWordsController extends Controller implements Initializable {

    @FXML
    private Button removeButton;
    @FXML
    private TextField wordTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ListenerAdder.setUpBasicTextFieldListeners(wordTextField, removeButton);
    }

    /**
     * Method to show information when info icon clicked
     * @param event button event
     */
    public void informationIcon(MouseEvent event) {
        AlertBox.newAlertBox("Removing Words",
                "You can remove words here by filling out the field below\n" +
                        "or you can go to src/resources/words/My_Words.txt\n" +
                        "and remove them by deleting the line they're on");
    }

    /**
     * Method that searches for entered word, and removes the word
     * from My_Words.txt if found when remove button is clicked
     * @param event Button Event
     */
    public void removeButton(ActionEvent event) {
        TextFieldManager textFieldManager = new TextFieldManager();

        //Checks for letters in each text box
        if (textFieldManager.isNoLetters(wordTextField.getText())) {
            AlertBox.newAlertBox("Error", "Word must have at least one letter.");
            wordTextField.clear();
            return;
        }

        //Requires user to confirm
        if (!ConfirmBox.newConfirmBox("Confirm Remove Word", "Are you sure you want to remove this word.")) {
            return;
        }

        WordFileManager wordFileManager = new WordFileManager();
        boolean isSuccess = wordFileManager.removeWordFromMyWords(wordTextField.getText().trim());
        if (isSuccess) {
            AlertBox.newAlertBox("Remove Word", "Word successfully removed.");
        } else {
            AlertBox.newAlertBox("Remove Word", "Word could not be found.");
        }
        wordTextField.clear();
    }

    /**
     * Method that deleted all words from My_Words.txt
     * @param event Button Event
     */
    public void removeAllButton(ActionEvent event) {

        //Asks user to confirm
        if (!ConfirmBox.newConfirmBox("Confirm Remove Word", "Are you sure you want to remove all your words.")) {
            return;
        }

        WordFileManager wordFileManager = new WordFileManager();
        wordFileManager.removeAllWordsFromMyWords();
        wordTextField.clear();
    }

}
