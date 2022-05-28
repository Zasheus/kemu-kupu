package application.utility;

import application.tablecontents.ViewWordsTableItem;
import application.trackers.CategoryTracker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for methods concerned with the word text files
 */
public class WordFileManager {

    /**
     * Method to check that the selected category exists and there are at least five words in the text file
     * @return true if exists and their are at least five words
     */
    public boolean isFiveWords (String category) {
        return isFileExists(category) && countNumLines(category) >= 5;
    }

    /**
     * Method to check if the currently selected category exists
     * @return true if exists
     */
    private boolean isFileExists(String category) {
        return new File("src/resources/words/" + category + ".txt").exists();
    }

    /**
     * Method to count the number of words in the currently selected category
     * @return Number of words
     */
    private int countNumLines(String category) {
        int numLines = 0;
        try {
            FileReader fr = new FileReader("src/resources/words/"+category+".txt");
            BufferedReader br = new BufferedReader(fr);

            while (br.readLine() != null) {
                numLines++;
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numLines;
    }

    /**
     * Method to build an ArrayList of all categories based on the text files in the category directory
     * This is necessary to populate the categories combobox
     * @return ArrayList of all categories
     */
    public ArrayList<String> getCategoryArrayList () {
        ArrayList<String> categoryArrayList = new ArrayList<>();
        File file = new File("src/resources/words");
        String[] categories = file.list();

        assert categories != null;
        for (String category : categories) {
            categoryArrayList.add(category.substring(0, category.length()-4).replaceAll("_", " "));
        }

        return categoryArrayList;
    }

    /**
     * Method to get ArrayList of a certain amount of words
     * @param wordAmount amount of words to add to list
     * @return list of words
     */
    public ArrayList<String> getWordsArrayList(int wordAmount) {
        ArrayList<String> words = new ArrayList<>();

        try {
            String category = CategoryTracker.getInstance().getCurrentCategory().replaceAll(" ", "_");
            FileReader fr = new FileReader("src/resources/words/"+category+".txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.split(" , ")[0]);
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.shuffle(words);
        if (words.size() > wordAmount) {
            words.subList(wordAmount, words.size()).clear();
        }

        return words;
    }

    /**
     * Method to get an arraylist of accompanying definitions to a word arraylist
     * @param words words to get definitions for
     * @return ArrayList of definitions
     */
    public ArrayList<String> getDefinitionsArrayList(ArrayList<String> words) {
        ArrayList<String> output = new ArrayList<>();
        String[] definitions = new String[words.size()];

        try {
            String category = CategoryTracker.getInstance().getCurrentCategory().replaceAll(" ", "_");
            FileReader fr = new FileReader("src/resources/words/"+category+".txt");
            BufferedReader br = new BufferedReader(fr);

            String[] tempArray;
            String line;
            while ((line = br.readLine()) != null) {
                tempArray = line.split(" , ");
                if (words.contains(tempArray[0])) {
                    definitions[words.indexOf(tempArray[0])] =  tempArray[1];
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.addAll(output, definitions);

        return output;
    }

    /**
     * Method to add a word to the my words file
     * @param word String to add
     * @param definition String definition of word
     */
    public void addWordToMyWords(String word, String definition) {
        try {
            FileWriter writer = new FileWriter("src/resources/words/My_Words.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(word + " , " + definition + System.getProperty("line.separator"));

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to remove a given word from the my words file
     * @param word String to remove
     * @return true if word is found
     */
    public boolean removeWordFromMyWords(String word) {
        boolean isWordFound = false;

        try {
            File inputFile = new File("src/resources/words/My_Words.txt");
            File tempFile = new File("src/resources/words/Temp.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (word.equals(line.split(" , ")[0])) {
                    isWordFound = true;
                    continue;
                }
                writer.write(line + System.getProperty("line.separator"));
            }

            reader.close();
            writer.close();

            tempFile.renameTo(inputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isWordFound;
    }

    /**
     * Checks to see if the given word exists in my words
     * @param word String to search for
     * @return true if word not found
     */
    public boolean doesWordExistInMyWords(String word) {
        try {
            File inputFile = new File("src/resources/words/My_Words.txt");
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (word.equals(line.split(" , ")[0])) {
                    reader.close();
                    return true;
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Method to remove all words from my words
     */
    public void removeAllWordsFromMyWords() {
        try {
            PrintWriter pw = new PrintWriter("src/resources/words/My_Words.txt");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get an observable arraylist for populating the view words table
     * @param category text file to get words and definitions from
     * @return observable arraylist for table
     */
    public ObservableList<ViewWordsTableItem> getObservableListForViewWords(String category) {
        ObservableList<ViewWordsTableItem> tableItems = FXCollections.observableArrayList();

        try {
            FileReader fr = new FileReader("src/resources/words/"+category.replaceAll(" ", "_")+".txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(" , ");
                tableItems.add(new ViewWordsTableItem(tempArr[0], tempArr[1]));
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tableItems;
    }

}