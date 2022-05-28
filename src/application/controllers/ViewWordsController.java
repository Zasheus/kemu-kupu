package application.controllers;

import application.guihelpers.ComboBoxManager;
import application.popups.AlertBox;
import application.tablecontents.ViewWordsTableItem;
import application.utility.WordFileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the reward screen
 */
public class ViewWordsController extends Controller implements Initializable {

	@FXML
	private ComboBox<String> categoryBox;

	@FXML
	private TableView<ViewWordsTableItem> table;
	@FXML
	private TableColumn<ViewWordsTableItem, String> wordColumn;
	@FXML
	private TableColumn<ViewWordsTableItem, String> definitionColumn;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ComboBoxManager.populateCategoryComboBox(categoryBox);
		setUpTable();
	}

	/**
	 * Method to setup the view words table
	 */
	private void setUpTable() {
		wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
		definitionColumn.setCellValueFactory(new PropertyValueFactory<>("definition"));

		stopTableReOrdering();
	}

	/**
	 * Method to prevent the re-ordering of table columns by the user
	 */
	private void stopTableReOrdering() {
		wordColumn.setReorderable(false);
		definitionColumn.setReorderable(false);
	}

	/**
	 * Method to handle what happens when info icon is clicked
	 * @param event Button event
	 */
	public void informationIcon(MouseEvent event) {
		AlertBox.newAlertBox("View Words",
				"Here you can view all the words and their\n" +
						"definitions. Just select a category from the\n" +
						"drop down and the table will be populated.");
	}

	/**
	 * Method to be called whenever the category combo box is changed
	 * @param event combo box change event
	 */
	public void categoryBox(ActionEvent event) {
		populateTable();
	}

	/**
	 * Method to populate the table
	 */
	private void populateTable() {
		WordFileManager wordFileManager = new WordFileManager();
		table.setItems(wordFileManager.getObservableListForViewWords(categoryBox.getValue()));
	}

}
