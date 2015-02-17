import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A utils class for LetterRC4, has methods for converting to and from the int representation of a character and
 * any needed constants.
 *
 * @author Dylan Bannon <drb2857@rit.edu>
 * 2/17/2015
 */
public class LetterRC4Util {

    /** the key size for LetterRC4 */
    public static final int KEY_SIZE = 26;
    /** static map for converting chars to their int representation */
    private static Map<Character, Integer> charToInt;
    /** static map for converting ints to their char representation */
    private static Map<Integer, Character> intToChar;
    // static constructor for the static maps
    static {
        Map<Character, Integer> tempMapToInt = new HashMap<>();
        Map<Integer, Character> tempMapToChar = new HashMap<>();
        int i = 0;
        for(char c = 'a'; c <= 'z'; c++) {
            tempMapToInt.put(c, i);
            tempMapToChar.put(i, c);
            i++;
        }
        charToInt = Collections.unmodifiableMap(tempMapToInt);
        intToChar = Collections.unmodifiableMap(tempMapToChar);
    }

    /**
     * Converts a given character to its int representation
     *
     * @param c the character to convert
     * @return the integer representation of c
     */
    public static int charToInt(Character c) {
        return charToInt.get(Character.toLowerCase(c));
    }

    /**
     * Converts a given int to its char representation
     *
     * @param i the int to convert
     * @return the char representation of i
     */
    public static char intToChar(int i) {
        return intToChar.get(i);
    }

    /**
     * Checks that the supplied key is in the proper format
     *
     * @param key the supplied key
     * @return true if the key is properly formatted, else false
     */
    public static boolean isKeyProperlyFormatted(String key) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        char[] keyArray = key.toCharArray();
        Arrays.sort(keyArray);
        String sortedKey = new String(keyArray);
        return alphabet.equalsIgnoreCase(sortedKey);
    }
}
