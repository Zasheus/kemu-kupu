package application.utility;

import application.tablecontents.LeaderboardTableItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Comparator;

/**
 * Class for methods concerned with the word text files
 */
public class LeaderboardFileManager {

    /**
     * Method to get an observable arraylist for populating the leaderboard
     * @return observable arraylist for table
     */
    public ObservableList<LeaderboardTableItem> getSortedObservableListForLeaderboard() {
        ObservableList<LeaderboardTableItem> tableItems = getObservableListOfLeaderboardEntries();

        LeaderboardTableItem.LeaderboardTableItemComparator leaderboardTableItemComparator = new LeaderboardTableItem.LeaderboardTableItemComparator();

        tableItems.sort(leaderboardTableItemComparator);

        return tableItems;
    }

    /**
     * Method to get an observable arraylist for populating the leaderboard
     * @return observable arraylist for table
     */
    private ObservableList<LeaderboardTableItem> getObservableListOfLeaderboardEntries() {
        ObservableList<LeaderboardTableItem> tableItems = FXCollections.observableArrayList();

        try {
            FileReader fr = new FileReader("src/resources/leaderboard/Leaderboard.txt");
            BufferedReader br = new BufferedReader(fr);

            String line;
            String[] tempArr;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(" , ");
                tableItems.add(new LeaderboardTableItem(tempArr[0], tempArr[1], tempArr[2], tempArr[3]));
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tableItems;
    }

    /**
     * Method to remove all entries from leaderboard
     */
    public void removeAllEntriesFromLeaderboard() {
        try {
            PrintWriter pw = new PrintWriter("src/resources/leaderboard/Leaderboard.txt");
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to save an entry to the leaderboard text file for use on the leaderboard
     * @param name String name of user
     * @param category String category of game
     * @param score String score of user
     * @param time String time taken by user
     */
    public void addLeaderboardEntry(String name, String category, String score, String time) {
        try {
            FileWriter writer = new FileWriter("src/resources/leaderboard/Leaderboard.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(name + " , " + category + " , " + score + " , " + time + System.getProperty("line.separator"));

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}