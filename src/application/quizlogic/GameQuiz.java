package application.quizlogic;

import application.trackers.QuizResultsTracker;

/**
 * Class containing all the logic to do with new quiz. Specifically for the game module
 */
public class GameQuiz extends Quiz{

    /**
     * Returns the current score as a string for label
     * @return myScore label content
     */
    public String scoreLabel() {
        return ("Score: " + score);
    }

    /**
     * Method to save time and results of words attempted in the game mode
     * so that they can be used at the reward screen at the end of the game
     * @param isSkipped If the word was skipped or attempted
     * @param time Time taken to spell the word
     */
    public void saveTimeAndResult(boolean isSkipped, int time) {
        if (isSkipped) {
            QuizResultsTracker.getInstance().addQuizResultSkipped(getCurrentWord());
        } else {
            QuizResultsTracker.getInstance().addQuizResult(getCurrentWord(), isCurrentWordCorrect, time);
        }
    }
}
