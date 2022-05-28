package application.tablecontents;

import java.util.Comparator;

/**
 * This class is used for populating the table in the leaderboard screen
 * Each instance of this class represents a row in the table
 */
public class LeaderboardTableItem {

    String name;
    String category;
    String score;
    String time;

    public LeaderboardTableItem(String name, String category, String score, String time) {
        this.name = name;
        this.category = category;
        this.score = score;
        this.time = time;
    }

    /**
     * Class for sorting table items in order first by score then time then name
     */
    public static class LeaderboardTableItemComparator implements Comparator<LeaderboardTableItem> {
        @Override
        public int compare(LeaderboardTableItem t1, LeaderboardTableItem t2) {

            int t1ScoreInt = Integer.parseInt(t1.getScore().split(" ")[0]);
            int t2ScoreInt = Integer.parseInt(t2.getScore().split(" ")[0]);
            if (t1ScoreInt < t2ScoreInt) {
                return 1;
            } else if (t1ScoreInt > t2ScoreInt) {
                return -1;
            }

            int t1TimeInt = Integer.parseInt(t1.getTime().split(" ")[0]);
            int t2TimeInt = Integer.parseInt(t2.getTime().split(" ")[0]);
            if (t1TimeInt < t2TimeInt) {
                return -1;
            } else if (t1TimeInt > t2TimeInt) {
                return 1;
            }

            return t1.getName().compareTo(t2.getName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
