package application.controllers;

import application.popups.AlertBox;
import application.popups.ConfirmBox;
import application.tablecontents.LeaderboardTableItem;
import application.utility.LeaderboardFileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the reward screen
 */
public class LeaderboardController extends Controller implements Initializable {

	@FXML
	private TableView<LeaderboardTableItem> table;
	@FXML
	private TableColumn<LeaderboardTableItem, String> nameColumn;
	@FXML
	private TableColumn<LeaderboardTableItem, String> categoryColumn;
	@FXML
	private TableColumn<LeaderboardTableItem, String> scoreColumn;
	@FXML
	private TableColumn<LeaderboardTableItem, String> timeColumn;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		buildTable();
	}

	/**
	 * Method to populate and setup the results table
	 */
	private void buildTable() {
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

		stopTableReOrdering();

		LeaderboardFileManager leaderboardFileManager = new LeaderboardFileManager();
		table.setItems(leaderboardFileManager.getSortedObservableListForLeaderboard());
	}

	/**
	 * Method to prevent the re-ordering of table columns by the user
	 */
	private void stopTableReOrdering() {
		nameColumn.setReorderable(false);
		categoryColumn.setReorderable(false);
		scoreColumn.setReorderable(false);
		timeColumn.setReorderable(false);
	}

	/**
	 * Method to handle what happens when info icon is clicked
	 * @param event Button event
	 */
	public void informationIcon(MouseEvent event) {
		AlertBox.newAlertBox("Leaderboard",
				"This is the leaderboard, entries are added each\n" +
						"time a user saves their score after completing\n" +
						"the game module. Entries can be cleared by\n" +
						"clicking the clear leaderboard button");
	}

	/**
	 * Method to clear the leaderboard and update it when the clear button is pressed
	 * @param event Button Event
	 */
	public void clearLeaderboardButton(ActionEvent event) {
		LeaderboardFileManager leaderboardFileManager = new LeaderboardFileManager();
		if (ConfirmBox.newConfirmBox("Confirm Clear", "Are you sure you want to clear your leaderboard?\nThis action cannot be undone.")) {
			leaderboardFileManager.removeAllEntriesFromLeaderboard();
			table.setItems(leaderboardFileManager.getSortedObservableListForLeaderboard());
		}
	}

}
