package application.controllers;

import application.popups.AlertBox;
import application.quizlogic.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.guihelpers.ButtonDisabler;
import application.guihelpers.ListenerAdder;
import application.utility.TextFieldManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the quiz scene
 */
public class PractiseQuizController extends QuizController implements Initializable {

	@FXML
	private Button nextWordButton;

	//Initialise methods

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		quiz = new PractiseQuiz();
		buttonDisabler = new ButtonDisabler(repeatButton, skipButton, submitButton, nextWordButton);
		currentSpeed = 1;

		populateGuiElements();
		ListenerAdder.setUpPractiseQuizScreenListeners(textField, submitButton, nextWordButton, quiz);

		readWord("Please spell");
	}

	//Main handler methods

	/**
	 * Method to skip current word and go to the next word when skip word button clicked
	 * @param event Button event
	 */
	@Override
	public void skipWordButton(ActionEvent event) {
		showWordState(true);
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

		if (((PractiseQuiz) quiz).lastWordWrong()) {
			showWordState(false);
		} else {
			quiz.processInput();
			if (updateGui(event)) {
				readWord(quiz.readCorrectOrIncorrect() + ". Please spell");
			}
		}
	}

	/**
	 * Method to check input on next word button and run through the processes
	 * to go to the next word or the next scene
	 * @param event Button event
	 * @throws IOException Throws exception if cant load scene
	 */
	public void nextWordButton(ActionEvent event) throws IOException {
		changeGuiToShowWordState(false);
		if (updateGui(event)) {
			readWord("Please spell");
		}
	}

	/**
	 * Method to handle what happens when info icon is clicked
	 * @param event Button event
	 */
	public void informationIcon(MouseEvent event) {
		AlertBox.newAlertBox("Practise Mode",
				"In practise mode you are not timed or scored.\n" +
						"You can type a circumflex (^) after a vowel to\n" +
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
			loadScene(event, "/resources/fxml/PractiseReward.fxml");
			return false;
		}
		updateLabels();
		return true;
	}

	/**
	 * Method to execute when the user gets the answer wrong twice and the word will be shown to them
	 * @param isSkipped true if word was skipped
	 */
	private void showWordState(boolean isSkipped) {
		changeGuiToShowWordState(true);
		myHint.setText(((PractiseQuiz) quiz).showFullWord());

		if (!isSkipped) {
			quiz.processInput();
			quiz.readOnePhrase(quiz.readCorrectOrIncorrect());
			quiz.getBackgroundWordRead().setOnSucceeded(e -> {
				nextWordButton.setDisable(false);
				nextWordButton.setOpacity(1);
			});
		} else {
			quiz.skipWord();
			nextWordButton.setDisable(false);
			nextWordButton.setOpacity(1);
		}
	}

	/**
	 * Method to change gui elements when the user is shown the whole word
	 * @param isNextButtonMode boolean true if next button mode is active
	 */
	private void changeGuiToShowWordState(boolean isNextButtonMode) {
		submitButton.setVisible(!isNextButtonMode);
		nextWordButton.setVisible(isNextButtonMode);
		textField.setEditable(!isNextButtonMode);
		buttonDisabler.changeButtonDisabled(isNextButtonMode);
		if (isNextButtonMode && quiz.isLastWord()) {
			nextWordButton.setText("end quiz");
		}
	}

}
