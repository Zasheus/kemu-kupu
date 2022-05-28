package application.quizlogic;

/**
 * Class containing all the logic to do with new quiz. Specifically for the practise module
 */
public class PractiseQuiz extends Quiz {

    /**
     * Returns label string with length of word
     * and gives second letter of word as hint if final attempt
     * @return myHint label content
     */
    @Override
    public String hintText() {
        int wordLength = getCurrentWord().length();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < wordLength-1; i++) {
            if (!Character.isWhitespace(getCurrentWord().charAt(i))){
                stringBuilder.append("_ ");
            }else {
                stringBuilder.append("  ");
            }
        }
        stringBuilder.append("_");

        //Add a line break after 50 characters so doesn't go off screen
        if (stringBuilder.length() > 50) {
            int num = stringBuilder.substring(0, 51).lastIndexOf("   ");
            stringBuilder.replace(num, num+3, "\n");
        }

        if (!isFirstAttempt) {
            stringBuilder.replace(2, 3, String.valueOf(getCurrentWord().charAt(1)));
        }

        return stringBuilder.toString();
    }

    /**
     * Returns string of the whole word for the hint label after the words wrong twice
     * @return myHint label content
     */
    public String showFullWord() {
        int wordLength = getCurrentWord().length();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < wordLength-1; i++) {
            stringBuilder.append(getCurrentWord().charAt(i)).append(" ");
        }
        stringBuilder.append(getCurrentWord().charAt(wordLength-1));

        //Add a line break after 50 characters so doesn't go off screen
        if (stringBuilder.length() > 50) {
            int num = stringBuilder.substring(0, 51).lastIndexOf("   ");
            stringBuilder.replace(num, num+3, "\n");
        }

        return stringBuilder.toString();
    }

    /**
     * Method to check if last word is wrong in practise mode
     * @return true is last word wrong
     */
    public boolean lastWordWrong() {
        return !isCurrentWordCorrect && !isFirstAttempt;
    }

}
