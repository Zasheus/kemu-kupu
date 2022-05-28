package application.guihelpers;

import application.utility.WordFileManager;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

/**
 * Class for filling and changing the settings of combo box's
 */
public class ComboBoxManager {

    /**
     * Method that initializes the text to speech speed combo box
     * @param speedBox ComboBox to populate
     */
    public static void populateQuizSpeedComboBox(ComboBox<String> speedBox) {
        double[] speeds = {0.25, 0.5, 0.75, 1, 1.25, 1.5, 1.75, 2};
        for (double speed : speeds) {
            speedBox.getItems().add(speed+"x");
        }
        speedBox.getSelectionModel().select(3);
    }

    /**
     * Method that initializes the category combo box with My Words always
     * present and always at the top
     * @param categoryBox ComboBox to populate
     */
    public static void populateCategoryComboBox(ComboBox<String> categoryBox) {
        WordFileManager wordFileManager = new WordFileManager();
        ArrayList<String> categories = wordFileManager.getCategoryArrayList();

        categories.remove("My Words");
        categoryBox.getItems().add("My Words");

        for (String category : categories) {
            categoryBox.getItems().add(category);
        }

        categoryBox.setVisibleRowCount(6);
    }
}
