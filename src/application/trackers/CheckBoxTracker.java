package application.trackers;

/**
 * Singleton for storing the current state of check box settings
 */
public class CheckBoxTracker {

    // Static variable reference of single_instance
    private static CheckBoxTracker singleInstance = null;

    private boolean isDisablePleaseSpell;
    private boolean isDisableDefinitions;

    //Constructor is private
    private CheckBoxTracker() {
        isDisablePleaseSpell = false;
        isDisableDefinitions = false;
    }

    /**
     * Static method to create/get instance of the Singleton class
     * @return the Singleton
     */
    public static CheckBoxTracker getInstance() {
        if (singleInstance == null) singleInstance = new CheckBoxTracker();

        return singleInstance;
    }

    public boolean isDisablePleaseSpell() {
        return isDisablePleaseSpell;
    }

    public void setDisablePleaseSpell(boolean disablePleaseSpell) {
        isDisablePleaseSpell = disablePleaseSpell;
    }

    public boolean isDisableDefinitions() {
        return isDisableDefinitions;
    }

    public void setDisableDefinitions(boolean disableDefinitions) {
        isDisableDefinitions = disableDefinitions;
    }
}
