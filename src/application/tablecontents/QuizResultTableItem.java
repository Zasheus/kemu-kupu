package application.tablecontents;

/**
 * This class is used for populating the table in the reward screen
 * Each instance of this class represents a row in the table
 */
public class QuizResultTableItem {

    String word;
    String result;
    String time;

    public QuizResultTableItem(String word, String result, String time) {
        this.word = word;
        this.result = result;
        this.time = time;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
