package application.quizlogic;

import application.utility.WordFileManager;

import java.util.ArrayList;

/**
 * Class containing all the logic to do with new quiz. Such as checking input,
 * keeping score, and more.
 */
public abstract class Quiz {

	protected final ArrayList<String> words;
	protected final ArrayList<String> definitions;

	protected final int WORDS_IN_QUIZ = 5;
	protected int wordIndex;
	protected static int score;
	protected boolean isFirstAttempt;
	protected boolean isCurrentWordCorrect;

	protected BackgroundWordRead backgroundWordRead;

	public Quiz() {
		WordFileManager wordFileManager = new WordFileManager();
		this.words = wordFileManager.getWordsArrayList(WORDS_IN_QUIZ);
		this.definitions = wordFileManager.getDefinitionsArrayList(this.words);

		wordIndex = 0;
		score = 0;
		isFirstAttempt = true;
		isCurrentWordCorrect = false;
	}

	/**
	 * Method to take user input and check if its correct
	 * @param userInput the string inputted by the user
	 */
	public void checkCorrect(String userInput) {
		isCurrentWordCorrect = checkInput(userInput);
	}

	/**
	 * Method to update fields based on user input
	 */
	public void processInput() {
		if (isCurrentWordCorrect) {
			wordIndex++;
			score++;
			isFirstAttempt = true;
		}else {
			if (isFirstAttempt) {
				isFirstAttempt = false;
			} else {
				wordIndex++;
				isFirstAttempt = true;
			}
		}
	}

	/**
	 * Returns string with length of word for the hint label
	 * @return myHint label content
	 */
	public String hintText() {
		int wordLength = getCurrentWord().length();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < wordLength-1; i++) {
			if (!Character.isWhitespace(getCurrentWord().charAt(i))){
				stringBuilder.append("_ ");
			}else {
				stringBuilder.append("  ");
			}
		}
		stringBuilder.append("_");

		//Add a line break after 50 characters so doesn't go off screen
		if (stringBuilder.length() > 50) {
			int num = stringBuilder.substring(0, 51).lastIndexOf("   ");
			stringBuilder.replace(num, num+3, "\n");
		}

		return stringBuilder.toString();
	}

	/**
	 * Method which returns the corresponding String if correct or incorrect
	 * @return the String that will be read prior to the next word
	 */
	public String readCorrectOrIncorrect() {
		if (isCurrentWordCorrect) {
			return "Correct";
		}else {
			if (!isFirstAttempt) {
				return "Incorrect. try once more";
			}else {
				return "Incorrect";
			}
		}
	}

	//Methods for reading words

	/**
	 * Method that reads the phrase parsed in, followed by current word and its definition
	 * @param firstPhrase the phrase to be read prior to the current word
	 */
	public void readWord(String firstPhrase) {
		backgroundWordRead = new BackgroundWordRead(
				firstPhrase, Language.ENGLISH,
				getCurrentWord(), Language.MAORI,
				getCurrentDefinitionPhrase(), Language.ENGLISH);
		Thread thread = new Thread(backgroundWordRead);
		thread.start();
	}

	/**
	 * Method that just reads correct or incorrect at the end of the quiz
	 * @param firstPhrase Correct or incorrect to read
	 */
	public void readOnePhrase(String firstPhrase) {
		backgroundWordRead = new BackgroundWordRead(firstPhrase, Language.ENGLISH);
		Thread thread = new Thread(backgroundWordRead);
		thread.start();
	}

	//Helper Methods

	/**
	 * Returns String to represent word number
	 * @return myScore label content
	 */
	public String wordNumLabel() {
		return ("Word " + (wordIndex + 1) + " of " + words.size());
	}

	/**
	 * Method to check if the quiz is over
	 * @return True if over
	 */
	public boolean isQuizOver() {
		return wordIndex >= WORDS_IN_QUIZ;
	}

	/**
	 * Checks if input matches current word
	 * @param userInput String inputted by the user
	 * @return If correct or not
	 */
	public boolean checkInput(String userInput) {
		return userInput.equalsIgnoreCase(getCurrentWord());
	}

	/**
	 * Method to build a phrase for the definition
	 * @return definition in a sentence
	 */
	public String getCurrentDefinitionPhrase () {
		return "This means, "+getCurrentDefinition();
	}

	/**
	 * Method to check if quiz is moving to next word
	 * @return true if moving to next word
	 */
	public boolean isMovingToNextWord() {
		return isQuizOver() || isCurrentWordCorrect || !isFirstAttempt;
	}

	/**
	 * Method to move the quiz to the next word
	 */
	public void skipWord () {
		isFirstAttempt = true;
		wordIndex++;
	}

	public boolean isLastWord() {
		return wordIndex == WORDS_IN_QUIZ-1;
	}

	public String getPreviousWord () {
		return words.get(wordIndex-1);
	}

	public String getCurrentWord () {
		return words.get(wordIndex);
	}

	public String getCurrentDefinition () {
		return definitions.get(wordIndex);
	}

	public static int getScore() {
		return score;
	}

	public BackgroundWordRead getBackgroundWordRead() {
		return backgroundWordRead;
	}

}
