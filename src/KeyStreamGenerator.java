/**
 * A keystream generator for Letter RC4
 *
 * @author Dylan Bannon <drb2857@rit.edu>
 * 2/16/2015
 */
public class KeyStreamGenerator {

    /** the state array, initial state is key */
    private int[] state;
    /** index for PRGA */
    private int i;
    /** second index for PRGA */
    private int j;

    /**
     * Constructor, sets initial values of state array and indices
     *
     * @param key the key for the generator
     */
    public KeyStreamGenerator(String key) {
        state = new int[key.length()];
        for(int i = 0; i < key.length(); i++) {
            state[i] = LetterRC4Util.charToInt(key.charAt(i));
        }
        i = 0;
        j = 0;
    }

    /**
     * Gets the next keystream character based on the stored state
     *
     * @return the next keystream character
     */
    public int getNextKeystreamValue() {
        i = (i + 1) % LetterRC4Util.KEY_SIZE;
        j = (j + state[i]) % LetterRC4Util.KEY_SIZE;
        // swap state[i] and state[j]
        int temp = state[i];
        state[i] = state[j];
        state[j] = temp;
        return state[(state[i] + state[j]) % LetterRC4Util.KEY_SIZE];
    }
}
