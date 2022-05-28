package application.controllers;

import application.popups.AlertBox;
import application.guihelpers.ComboBoxManager;
import application.trackers.CheckBoxTracker;
import application.utility.WordFileManager;
import application.trackers.CategoryTracker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the categories scene
 */
public class CategoriesController extends Controller implements Initializable {

    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private CheckBox disablePleaseSpellCheckBox;
    @FXML
    private CheckBox disableDefinitionsCheckBox;

    //Initialise Methods

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ComboBoxManager.populateCategoryComboBox(categoryBox);
        disablePleaseSpellCheckBox.setSelected(CheckBoxTracker.getInstance().isDisablePleaseSpell());
        disableDefinitionsCheckBox.setSelected(CheckBoxTracker.getInstance().isDisableDefinitions());
    }

    //Button Methods

    /**
     * Method to handle what happens when info icon is clicked
     * @param event Button event
     */
    public void informationIcon(MouseEvent event) {
        AlertBox.newAlertBox("Category Selection Help",
                "You must select a category before moving to the quiz.\n" +
                        "Practise mode is not timed and not scored. Game mode \n" +
                        "is timed and scored. Disabling \"please spell\" or\n" +
                        "definitions will change how words are read in the quiz.");
    }

    /**
     * Method to save combo box value in Singleton and move into practise mode
     * @param event Button event
     * @throws Exception If loading scene fails
     */
    public void practiseModeButton(ActionEvent event) throws Exception {

        if (!inputCheck()) return;

        CategoryTracker.getInstance().setCurrentCategory(categoryBox.getValue());
        loadScene(event, "/resources/fxml/PractiseQuiz.fxml");
    }

    /**
     * Method to save combo box value in Singleton and move into game mode
     * @param event Button event
     * @throws Exception If loading scene fails
     */
    public void gameModeButton(ActionEvent event) throws Exception {

        if (!inputCheck()) return;

        CategoryTracker.getInstance().setCurrentCategory(categoryBox.getValue());
        loadScene(event, "/resources/fxml/GameQuiz.fxml");
    }

    /**
     * Method to save the value of the disable please spell checkbox whenever its value is changed
     * @param event Button event
     */
    public void disablePleaseSpellCheckBox(ActionEvent event) {
        CheckBoxTracker.getInstance().setDisablePleaseSpell(disablePleaseSpellCheckBox.isSelected());
    }

    /**
     * Method to save the value of the disable definitions checkbox whenever its value is changed
     * @param event Button event
     */
    public void disableDefinitionsCheckBox(ActionEvent event) {
        CheckBoxTracker.getInstance().setDisableDefinitions(disableDefinitionsCheckBox.isSelected());
    }

    //Helper Methods

    /**
     * Method to ensure the input in category combobox is valid before moving to next screen
     * @return false if input is invalid
     */
    private boolean inputCheck() {

        WordFileManager wordFileManager = new WordFileManager();

        if (categoryBox.getSelectionModel().isEmpty()) {
            AlertBox.newAlertBox("Invalid Category", "You must select a category.");
            return false;
        }
        if (!wordFileManager.isFiveWords(categoryBox.getValue().replaceAll(" ", "_"))) {
            AlertBox.newAlertBox("Invalid Category", "That category doesn't have 5 words in it.");
            return false;
        }

        return true;
    }

}
