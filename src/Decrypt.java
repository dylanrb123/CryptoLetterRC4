import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Decrypts a file using Letter-RC4
 *
 * @author <drb2857@rit.edu> Dylan Bannon
 * 2/13/2015
 */
public class Decrypt {

    /**
     * The main method. Deals with the command line args, checks error conditions, initializes I/O objects, and calls
     * the decryptFile method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 3) {
            System.err.println("Incorrect number of command line arguments.");
            System.err.println("Usage: java Decrypt <key> <CipherTextFile> <PlainTextFile>");
            System.exit(0);
        }
        // Store the command line args
        String key = args[0];
        String cipherTextFileName = args[1];
        String plainTextFileName = args[2];
        // Check that the key is properly formatter
        if(!LetterRC4Util.isKeyProperlyFormatted(key)) {
            System.err.println("Key is improperly formatted. Key must be a permutation of the letters A to Z, upper or lower case.");
            System.err.println("Usage: java Decrypt <key> <CipherTextFile> <PlainTextFile>");
            System.exit(0);
        }
        // initialize the input and output streams and the keystream generator
        FileInputStream in = null;
        FileOutputStream out = null;
        KeyStreamGenerator generator = new KeyStreamGenerator(key);
        try {
            in = new FileInputStream(cipherTextFileName);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + cipherTextFileName + "' not found.");
            System.err.println("Usage: java Decrypt <key> <CipherTextFile> <PlainTextFile>");
            System.exit(0);
        }
        try {
            out = new FileOutputStream(plainTextFileName);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + plainTextFileName + "' cannot be created.");
            System.err.println("Usage: java Decrypt <key> <CipherTextFile> <PlainTextFile>");
            System.exit(0);
        }
        // decrypt the file
        decryptFile(generator, in, out);
    }

    /**
     * Decrypts a whole file byte-by-byte, ignoring whitespace and non-letters
     *
     * @param keystream the keystream generator
     * @param in the input stream
     * @param out the output stream
     */
    private static void decryptFile(KeyStreamGenerator keystream, FileInputStream in, FileOutputStream out) {
        int inputByte;
        char cipherTextChar;
        try {
            // loop through the whole file, byte-by-byte
            while((inputByte = in.read()) != -1) {
                // interpret the input as a char
                cipherTextChar = (char) inputByte;
                char plainTextChar;
                // ignore whitespace and non-letter characters
                if(!Character.isWhitespace(cipherTextChar) && Character.isLetter(cipherTextChar)) {
                    plainTextChar = decryptChar(cipherTextChar, keystream);
                    out.write(Character.toUpperCase(plainTextChar));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing file.");
            System.exit(0);
        }
    }

    /**
     * Decrypts a single plaintext character given a keystream generator
     *
     * @param cipherTextChar the plaintext character to decrypt
     * @param generator the keystream generator
     * @return the calculated ciphertext character
     */
    private static char decryptChar(char cipherTextChar, KeyStreamGenerator generator) {
        // get the next keystream character
        int keystreamValue = generator.getNextKeystreamValue();
        // ciphertext = (plaintext + keystream) (mod KEY_SIZE) (adds the modulus back in to deal with mod of negative numbers)
        int plainTextValue = (((LetterRC4Util.charToInt(cipherTextChar) - keystreamValue) % LetterRC4Util.KEY_SIZE) + LetterRC4Util.KEY_SIZE)
                % LetterRC4Util.KEY_SIZE;
        return LetterRC4Util.intToChar(plainTextValue);
    }
}
