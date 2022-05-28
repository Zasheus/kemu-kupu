package application.tablecontents;

/**
 * This class is used for populating the table in the view words screen
 * Each instance of this class represents a row in the table
 */
public class ViewWordsTableItem {

    String word;
    String definition;

    public ViewWordsTableItem(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
