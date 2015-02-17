import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dylan Bannon <drb2857@rit.edu>
 *         2/17/2015
 */
public class LetterRC4Util {

    public static final int KEY_SIZE = 26;

    private static Map<Character, Integer> charToInt;
    private static Map<Integer, Character> intToChar;
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

    public static int charToInt(Character c) {
        return charToInt.get(Character.toLowerCase(c));
    }

    public static char intToChar(int i) {
        return intToChar.get(i);
    }
}
