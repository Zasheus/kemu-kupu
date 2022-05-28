package application.controllers;

import application.popups.AlertBox;
import application.popups.UserEntryBox;
import application.quizlogic.Quiz;
import application.tablecontents.QuizResultTableItem;
import application.trackers.CategoryTracker;
import application.trackers.QuizResultsTracker;
import application.utility.LeaderboardFileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the reward screen
 */
public class GameRewardController extends Controller implements Initializable {

	@FXML
	private Label myScore;
	@FXML
	private Button saveScoreButton;

	@FXML
	private TableView<QuizResultTableItem> table;
	@FXML
	private TableColumn<QuizResultTableItem, String> wordColumn;
	@FXML
	private TableColumn<QuizResultTableItem, String> resultColumn;
	@FXML
	private TableColumn<QuizResultTableItem, String> timeColumn;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		setScoreLabel();
		buildTable();
	}

	/**
	 * Method to set the text on the myScore label and
	 * disable the save score button if score is not save-able.
	 * The score is not save-able if any words were skipped.
	 */
	private void setScoreLabel() {
		String scoreLabelText = "Ka Pai you scored " + Quiz.getScore() + " out of 5";

		if (QuizResultsTracker.getInstance().getTotalTime() >= 0 && QuizResultsTracker.getInstance().getTotalTime() <= 5) {
			scoreLabelText += " in " + QuizResultsTracker.getInstance().getTotalTime() + " seconds";
			scoreLabelText += " that was lightening fast";
		} else if (QuizResultsTracker.getInstance().getTotalTime() >= 6 && QuizResultsTracker.getInstance().getTotalTime() <= 10) {
			scoreLabelText += " in " + QuizResultsTracker.getInstance().getTotalTime() + " seconds";
			scoreLabelText += " wow that was fast";
		} else if (QuizResultsTracker.getInstance().getTotalTime() >= 11 && QuizResultsTracker.getInstance().getTotalTime() <= 20) {
			scoreLabelText += " in " + QuizResultsTracker.getInstance().getTotalTime() + " seconds";
			scoreLabelText += " not bad";
		} else if (QuizResultsTracker.getInstance().getTotalTime() >= 21) {
			scoreLabelText += " in " + QuizResultsTracker.getInstance().getTotalTime() + " seconds";
			scoreLabelText += " maybe try to pick up the pace next time";
		} else {
			scoreLabelText += "\nYou must not skip any words to get a time and save your score";
			saveScoreButton.setDisable(true);
		}

		myScore.setText(scoreLabelText);
	}

	/**
	 * Method to populate and setup the results table
	 */
	private void buildTable() {
		wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
		resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

		stopTableReOrdering();

		table.setItems(QuizResultsTracker.getInstance().getQuizResults());
	}

	/**
	 * Method to prevent the re-ordering of table columns by the user
	 */
	private void stopTableReOrdering() {
		wordColumn.setReorderable(false);
		resultColumn.setReorderable(false);
		timeColumn.setReorderable(false);
	}

	/**
	 * Method to handle what happens when info icon is clicked
	 * @param event Button event
	 */
	public void informationIcon(MouseEvent event) {
		AlertBox.newAlertBox("Saving Score",
				"You can only save your score if you did not skip\n" +
						"any words. Saving your score will add it to the\n" +
						"leaderboard found in the leaderboard tab in the\n" +
						"navigation bar");
	}

	/**
	 * Method to save the users score
	 * @param event Button Event
	 */
	public void saveScoreButton(ActionEvent event) {
		String name;
		if ((name = UserEntryBox.newUserEntryBox("Name Entry", "Please enter your name")) == null) {
			return;
		}
		String category = CategoryTracker.getInstance().getCurrentCategory();
		String score = Quiz.getScore() + " out of 5";
		String time = QuizResultsTracker.getInstance().getTotalTime() + " seconds";

		LeaderboardFileManager leaderboardFileManager = new LeaderboardFileManager();
		leaderboardFileManager.addLeaderboardEntry(name, category, score, time);

		saveScoreButton.setDisable(true);
	}

}
