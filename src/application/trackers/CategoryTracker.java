package application.trackers;

/**
 * Singleton for storing the current category of the quiz
 */
public class CategoryTracker {

    // Static variable reference of single_instance
    private static CategoryTracker singleInstance = null;

    private String currentCategory;

    //Constructor is private
    private CategoryTracker() {
        currentCategory = "";
    }

    /**
     * Static method to create/get instance of the Singleton class
     * @return the Singleton
     */
    public static CategoryTracker getInstance() {
        if (singleInstance == null) singleInstance = new CategoryTracker();

        return singleInstance;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentCategory(String currentCategory) {
        this.currentCategory = currentCategory;
    }
}
