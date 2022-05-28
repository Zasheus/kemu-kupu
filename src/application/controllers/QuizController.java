package application.controllers;

import application.launch.Main;
import application.popups.ConfirmBox;
import application.quizlogic.Quiz;
import application.guihelpers.ButtonDisabler;
import application.trackers.CategoryTracker;
import application.guihelpers.ComboBoxManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Class with methods shared by all quiz controller classes
 */
public abstract class QuizController extends Controller {

	@FXML
	protected Button homeButton;
	@FXML
	protected Button newButton;
	@FXML
	protected Button quitButton;

	@FXML
	protected ComboBox<String> speedBox;
	@FXML
	protected TextField textField;
	@FXML
	protected Label myLabel;
	@FXML
	protected Label myScore;
	@FXML
	protected Label myHint;
	@FXML
	protected Label categoryLabel;

	@FXML
	protected Button repeatButton;
	@FXML
	protected Button submitButton;
	@FXML
	protected Button skipButton;

	protected Quiz quiz;
	protected ButtonDisabler buttonDisabler;

	protected static double currentSpeed;

	//Initialisation methods

	protected void populateGuiElements() {
		ComboBoxManager.populateQuizSpeedComboBox(speedBox);
		updateLabels();
		categoryLabel.setText(CategoryTracker.getInstance().getCurrentCategory());
	}

	//Main handler methods

	/**
	 * Method that reads the current word again through text to speech
	 * method is called when repeat button is hit
	 * @param event Button event
	 */
	public void repeatButton(ActionEvent event) {
		readWord(null);
	}

	/**
	 * Method to adjust text to speech speed called when combobox is changed
	 * @param event Button event
	 */
	public void speedChange(ActionEvent event) {
		currentSpeed = 1 / Double.parseDouble(speedBox.getValue().substring(0, speedBox.getValue().length()-1));
		currentSpeed = Math.round(currentSpeed*100.0)/100.0;
	}

	/**
	 * Method to skip current word and go to the next word
	 * @param event Button event
	 * @throws IOException Throws exception if cant load scene
	 */
	public abstract void skipWordButton(ActionEvent event) throws IOException;

	/**
	 * Method to check input on submit button and run through the processes
	 * to go to the next word or the next scene
	 * @param event Button event
	 * @throws IOException Throws exception if cant load scene
	 */
	public abstract void submit(ActionEvent event) throws IOException;

	//Helper methods

	/**
	 * Method to update Gui elements and move to next scene if necessary
	 * @param event Button event
	 * @throws IOException thrown if new scene cannot be loaded
	 * @return false if program moved to next scene
	 */
	protected abstract boolean updateGui(ActionEvent event) throws IOException;

	/**
	 * Method to read word and disable buttons before and after doing so
	 * @param firstPhrase phrase to read prior to word
	 */
	protected void readWord(String firstPhrase) {
		buttonDisabler.changeButtonDisabled(true);
		quiz.readWord(firstPhrase);
		quiz.getBackgroundWordRead().setOnSucceeded(e -> buttonDisabler.changeButtonDisabled(false));
	}

	/**
	 * Method that updates labels and clears text box
	 */
	protected void updateLabels() {
		myLabel.setText(quiz.wordNumLabel());
		myHint.setText(quiz.hintText());
		textField.clear();
	}

	public static double getCurrentSpeed() {
		return currentSpeed;
	}

	//Navbar Button Methods

	/**
	 * Method to quit the application with confirmation window
	 * @param event Button Event
	 */
	@Override
	public void quitButton(ActionEvent event) {
		if(ConfirmBox.newConfirmBox("Confirm Quit", "Are you sure you want to quit")) {
			quiz.getBackgroundWordRead().stopReading();
			Main.window.close();
		}
	}

	/**
	 * Method to go back to the home screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	@Override
	public void homeButton(ActionEvent event) throws IOException {
		if (ConfirmBox.newConfirmBox("Confirm Return Home", "Are you sure you want to return home?\nyour progress wont be saved.")) {
			loadScene(event, "/resources/fxml/Welcome.fxml");
			quiz.getBackgroundWordRead().stopReading();
		}
	}

	/**
	 * Method to go back to the category screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	@Override
	public void newGameButton(ActionEvent event) throws IOException {
		if (ConfirmBox.newConfirmBox("Confirm New Game", "Are you sure you want to start a new game?\nyour progress wont be saved.")) {
			loadScene(event, "/resources/fxml/Categories.fxml");
			quiz.getBackgroundWordRead().stopReading();
		}
	}

	/**
	 * Method to go to the add words screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	@Override
	public void addWordsButton(ActionEvent event) throws IOException {
		if (ConfirmBox.newConfirmBox("Confirm Add Words", "Are you sure you want to leave?\nyour progress wont be saved.")) {
			loadScene(event, "/resources/fxml/AddWords.fxml");
			quiz.getBackgroundWordRead().stopReading();
		}
	}

	/**
	 * Method to go to the remove words screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	@Override
	public void removeWordsButton(ActionEvent event) throws IOException {
		if (ConfirmBox.newConfirmBox("Confirm Remove Words", "Are you sure you want to leave?\nyour progress wont be saved.")) {
			loadScene(event, "/resources/fxml/RemoveWords.fxml");
			quiz.getBackgroundWordRead().stopReading();
		}
	}

	/**
	 * Method to go to the view words screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	@Override
	public void viewWordsButton(ActionEvent event) throws IOException {
		if (ConfirmBox.newConfirmBox("Confirm View Words", "Are you sure you want to leave?\nyour progress wont be saved.")) {
			loadScene(event, "/resources/fxml/ViewWords.fxml");
			quiz.getBackgroundWordRead().stopReading();
		}
	}

	/**
	 * Method to go to the leaderboard screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	@Override
	public void leaderboardButton(ActionEvent event) throws IOException {
		if (ConfirmBox.newConfirmBox("Confirm Leaderboard", "Are you sure you want to leave?\nyour progress wont be saved.")) {
			loadScene(event, "/resources/fxml/Leaderboard.fxml");
			quiz.getBackgroundWordRead().stopReading();
		}
	}

}
