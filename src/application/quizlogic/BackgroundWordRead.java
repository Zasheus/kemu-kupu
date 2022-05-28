package application.quizlogic;

import application.controllers.PractiseQuizController;
import application.trackers.CheckBoxTracker;
import javafx.concurrent.Task;

import java.io.IOException;

/**
 * Class that will read phrases aloud in the background
 * this class also extends task and can be run in the background
 */
public class BackgroundWordRead extends Task {

    private final String firstPhrase;
    private final Language firstLanguage;
    private final String secondPhrase;
    private final Language secondLanguage;
    private final String thirdPhrase;
    private final Language thirdLanguage;

    private Process readingProcess;

    private boolean running;

    public BackgroundWordRead(String firstPhrase, Language firstLanguage) {
        this.firstPhrase = firstPhrase;
        this.firstLanguage = firstLanguage;

        this.secondPhrase = null;
        this.secondLanguage = null;
        this.thirdPhrase = null;
        this.thirdLanguage = null;

        running = true;
    }

    /**
     * Standard constructor that takes three phrases and languages to read sequentially
     * @param firstPhrase first phrase to be read
     * @param firstLanguage language of first phrase
     * @param secondPhrase second phrase to be read
     * @param secondLanguage language of second phrase
     * @param thirdPhrase third phrase to be read
     * @param thirdLanguage language of third phrase
     */
    public BackgroundWordRead(String firstPhrase, Language firstLanguage, String secondPhrase, Language secondLanguage, String thirdPhrase, Language thirdLanguage) {
        if (CheckBoxTracker.getInstance().isDisablePleaseSpell() && firstPhrase != null) {
            if (firstPhrase.equals("Please spell")) {
                firstPhrase = null;
                firstLanguage = null;
            } else {
                firstPhrase = firstPhrase.replaceFirst(". Please spell", "");
            }
        }
        if (CheckBoxTracker.getInstance().isDisableDefinitions()) {
            thirdPhrase = null;
            thirdLanguage = null;
        }

        this.firstPhrase = firstPhrase;
        this.firstLanguage = firstLanguage;
        this.secondPhrase = secondPhrase;
        this.secondLanguage = secondLanguage;
        this.thirdPhrase = thirdPhrase;
        this.thirdLanguage = thirdLanguage;

        running = true;
    }

    /**
     * Method to read the given phrase aloud
     * It overrides of the call method so it can run in the background
     * @return null always
     */
    @Override
    protected Object call() {

        if (firstPhrase != null && firstLanguage != null && running) {
            readPhrase(firstLanguage, firstPhrase);
        }

        if (secondPhrase != null && secondLanguage != null && running) {
            readPhrase(secondLanguage, secondPhrase);
        }

        if (thirdPhrase != null && thirdLanguage != null && running) {
            readPhrase(thirdLanguage, thirdPhrase);
        }

        return null;
    }

    /**
     * Method to speak aloud phrase
     * @param language Language of phrase
     * @param phrase String to say aloud
     */
    private void readPhrase(Language language, String phrase) {
        String speedForPb = "(Parameter.set 'Duration_Stretch " + PractiseQuizController.getCurrentSpeed() + ")";
        String phraseForPb = "(SayText \\\"" + phrase.replaceAll("-", " ") + "\\\")";
        String languageForPb;

        switch (language) {
            case MAORI:
                languageForPb = "(voice_akl_mi_pk06_cg)";
                break;
            case ENGLISH:
            default:
                languageForPb = "(voice_kal_diphone)";
        }

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", "-c",
                    "echo \"" + languageForPb + " " + speedForPb + " " + phraseForPb + "\" | festival");
            readingProcess = processBuilder.start();
            readingProcess.waitFor();
        } catch (Exception e) {
			e.printStackTrace();
        }
    }

    /**
     * Method to stop festival reading whatever it is currently reading
     */
    public void stopReading () {
        running = false;

        try {
            String[] command = new String[]{"/bin/bash", "-c", "a=$(pgrep festival) ; b=$(pgrep -P $a) ; c=$(pgrep -P $b) ; kill $a $b $c"};
            ProcessBuilder pb = new ProcessBuilder(command);
            Process killingProcess = pb.start();
            killingProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        readingProcess.destroy();
    }
}