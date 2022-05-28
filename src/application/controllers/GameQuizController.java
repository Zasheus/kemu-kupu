package application.controllers;

import application.popups.AlertBox;
import application.quizlogic.GameQuiz;
import application.quizlogic.QuizTimer;
import application.guihelpers.ButtonDisabler;
import application.guihelpers.ListenerAdder;
import application.trackers.QuizResultsTracker;
import application.utility.TextFieldManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the quiz scene, handles all the methods called by user input via buttons or text
 */
public class GameQuizController extends QuizController implements Initializable {

	@FXML
	private Label timeLabel;

	private QuizTimer quizTimer;

	//Initialise methods

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		quiz = new GameQuiz();
		quizTimer = new QuizTimer(timeLabel);
		buttonDisabler = new ButtonDisabler(repeatButton, skipButton, submitButton);
		currentSpeed = 1;

		populateGuiElements();
		ListenerAdder.setUpGameQuizScreenListeners(textField, submitButton, quiz);

		QuizResultsTracker.getInstance().clearQuizResults();

		readWord("Please spell");
	}

	//Main handler methods

	/**
	 * Method to skip current word and go to the next word when skip button clicked
	 * @param event Button event
	 * @throws IOException Throws exception if cant load scene
	 */
	@Override
	public void skipWordButton(ActionEvent event) throws IOException {
		quizTimer.updateTimerAndResults(quiz, true);
		quiz.skipWord();

		if (updateGui(event)) {
			readWord("Please spell");
		}
	}

	/**
	 * Method to check input on submit button and run through the processes
	 * to go to the next word or the next scene
	 * @param event Button event
	 * @throws IOException Throws exception if cant load scene
	 */
	@Override
	public void submit(ActionEvent event) throws IOException {
		TextFieldManager textFieldManager = new TextFieldManager();
		if (textFieldManager.isNoLetters(textField.getText())) return;

		quiz.checkCorrect(textField.getText().trim());

		if (quiz.isMovingToNextWord()) {
			quizTimer.updateTimerAndResults(quiz, false);
		}

		quiz.processInput();

		if (updateGui(event)) {
			readWord(quiz.readCorrectOrIncorrect() + ". Please spell");
		}
	}

	/**
	 * Method to handle what happens when info icon is clicked
	 * @param event Button event
	 */
	public void informationIcon(MouseEvent event) {
		AlertBox.newAlertBox("Game Mode",
				"In game mode you are timed and scored. You\n" +
						"can type a circumflex (^) after a vowel to\n" +
						"macronise that vowel.");
	}

	//Helper Methods

	/**
	 * Method to update Gui elements and move to next scene if necessary
	 * @param event Button event
	 * @throws IOException thrown if new scene cannot be loaded
	 * @return false if program moved to next scene
	 */
	@Override
	protected boolean updateGui(ActionEvent event) throws IOException {
		if (quiz.isQuizOver()) {
			if (event.getSource() == submitButton) quiz.readOnePhrase(quiz.readCorrectOrIncorrect());
			loadScene(event, "/resources/fxml/GameReward.fxml");
			return false;
		}
		updateLabels();
		return true;
	}

	/**
	 * Method to read word as well as disable buttons and pause timer
	 * before and resume them after reading has finished
	 * @param firstPhrase phrase to read prior to word
	 */
	@Override
	protected void readWord(String firstPhrase) {
		buttonDisabler.changeButtonDisabled(true);
		quizTimer.pauseTimer();
		quiz.readWord(firstPhrase);
		quiz.getBackgroundWordRead().setOnSucceeded(e -> {
			buttonDisabler.changeButtonDisabled(false);
			quizTimer.resumeTimer();
		});
	}

	/**
	 * Method that updates labels and clears text box
	 */
	@Override
	protected void updateLabels() {
		myLabel.setText(quiz.wordNumLabel());
		myScore.setText(((GameQuiz) quiz).scoreLabel());
		myHint.setText(quiz.hintText());
		textField.clear();
	}

}
