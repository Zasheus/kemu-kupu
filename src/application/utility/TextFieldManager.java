package application.utility;

/**
 * Class to manage all complexity to do with text fields
 */
public class TextFieldManager {

    /**
     * Method to check for a character to be macronised
     * @param oldValue String before last character was added
     * @param newValue String after last character was added
     * @return Index of character of -1 if cant be found
     */
    public int checkForMacronise (String oldValue, String newValue) {
        if (!(oldValue.length() > 0 && newValue.length() > 0)) return -1;

        char[] chars = newValue.toCharArray();
        //Start from second letter because there shouldn't be a carat at the start
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == '^') {
                if (isVowel(chars[i-1])) {
                    return i-1;
                }
            }
        }

        return -1;
    }

    /**
     * Method to macronise a character in a string
     * @param string String to be changed
     * @return Original string with selected character macronised
     */
    public String macroniseCharacter(int indexToMacronise, String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.setCharAt(indexToMacronise, convertToMacronised(string.charAt(indexToMacronise)));

        return stringBuilder.toString();
    }

    /**
     * Method that checks if given string contains only letters whitespace and dash's
     * @param string string to check
     * @return true if only letters, space and dash's
     */
    public boolean isLegalCharacter(String string) {
        char[] chars = string.toCharArray();

        for (char c : chars) {
            if(!(Character.isLetter(c) || Character.isWhitespace(c) || c == '-' || c == ',')) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method that checks if given string contains letters
     * @param string string to check
     * @return true if only letters, space and dash's
     */
    public boolean isNoLetters(String string) {

        char[] chars = string.toCharArray();

        for (char c : chars) {
            if(Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method to get the last character of a string
     * @param string string to extract from
     * @return last character
     */
    private char lastCharacter (String string) {
        return string.charAt(string.length()-1);
    }

    /**
     * Method to check if a character is a vowel
     * @param character char to check
     * @return true if vowel
     */
    private boolean isVowel (char character) {
        return character == 'A' ||
                character == 'a' ||
                character == 'E' ||
                character == 'e' ||
                character == 'I' ||
                character == 'i' ||
                character == 'O' ||
                character == 'o' ||
                character == 'U' ||
                character == 'u';
    }

    /**
     * Method to convert a vowel to a macronised version of itself
     * If character is not a vowel it is returned unchanged
     * @param charAt vowel to be macronised
     * @return macronised vowel
     */
    private char convertToMacronised(char charAt) {

        switch (charAt) {
            case 'a':
                return 'ā';
            case 'A':
                return 'Ā';
            case 'e':
                return 'ē';
            case 'E':
                return 'Ē';
            case 'i':
                return 'ī';
            case 'I':
                return 'Ī';
            case 'o':
                return 'ō';
            case 'O':
                return 'Ō';
            case 'u':
                return 'ū';
            case 'U':
                return 'Ū';
            default:
                return charAt;
        }
    }
}
