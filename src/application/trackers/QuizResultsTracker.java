package application.trackers;

import application.tablecontents.QuizResultTableItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton for storing the results of each question to be displayed in a table at the end
 */
public class QuizResultsTracker {

    // Static variable reference of single_instance
    private static QuizResultsTracker singleInstance = null;

    private ObservableList<QuizResultTableItem> quizResults;
    private int totalTime;

    //Constructor is private
    private QuizResultsTracker() {
        quizResults = FXCollections.observableArrayList();
        totalTime = 0;
    }

    /**
     * Static method to create/get instance of the Singleton class
     * @return the Singleton
     */
    public static QuizResultsTracker getInstance() {
        if (singleInstance == null) singleInstance = new QuizResultsTracker();

        return singleInstance;
    }

    /**
     * Method to add a quiz result to the observable list formatted correctly
     * @param word word that was attempted
     * @param result correct or incorrect
     * @param time time taken
     */
    public void addQuizResult(String word, boolean result, int time) {
        totalTime += time;
        String resultFormatted;
        if (result) {
            resultFormatted = "Correct";
        } else {
            resultFormatted = "Incorrect";
        }
        String timeFormatted = time+" seconds";
        quizResults.add(new QuizResultTableItem(word, resultFormatted, timeFormatted));
    }

    /**
     * Method to add a quiz result to the observable list formatted correctly
     * @param word word that was attempted
     */
    public void addQuizResultSkipped(String word) {
        totalTime = Integer.MIN_VALUE; //Total time is disregarded when a word is skipped
        String resultFormatted = "Did not attempt";
        String timeFormatted = "Did not attempt";
        quizResults.add(new QuizResultTableItem(word, resultFormatted, timeFormatted));
    }

    public void clearQuizResults() {
        quizResults = FXCollections.observableArrayList();
        totalTime = 0;
    }

    public ObservableList<QuizResultTableItem> getQuizResults() {
        return quizResults;
    }

    public int getTotalTime() {
        return totalTime;
    }
}
