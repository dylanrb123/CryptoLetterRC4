import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Encrypts a file using Letter-RC4
 *
 * @author Dylan Bannon <drb2857@rit.edu>
 * 2/13/2015
 */
public class Encrypt {

    /**
     * The main method. Deals with the command line args, checks error conditions, initializes I/O objects, and calls
     * the encryptFile method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 3) {
            System.err.println("Incorrect number of command line arguments.");
            System.err.println("Usage: java Encrypt <key> <PlainTextFile> <CipherTextFile>");
            System.exit(0);
        }
        // Store the command line args
        String key = args[0];
        String plainTextFileName = args[1];
        String cipherTextFileName = args[2];
        // Check that the key is properly formatter
        if(!LetterRC4Util.isKeyProperlyFormatted(key)) {
            System.err.println("Key is improperly formatted. Key must be a permutation of the letters A to Z, upper or lower case.");
            System.err.println("Usage: java Encrypt <key> <PlainTextFile> <CipherTextFile>");
            System.exit(0);
        }
        // initialize the input and output streams and the keystream generator
        FileInputStream in = null;
        FileOutputStream out = null;
        KeyStreamGenerator generator = new KeyStreamGenerator(key);
        try {
            in = new FileInputStream(plainTextFileName);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + plainTextFileName + "' not found.");
            System.err.println("Usage: java Encrypt <key> <PlainTextFile> <CipherTextFile>");
            System.exit(0);
        }
        try {
            out = new FileOutputStream(cipherTextFileName);
        } catch (FileNotFoundException e) {
            System.err.println("File '" + cipherTextFileName + "' cannot be created.");
            System.err.println("Usage: java Encrypt <key> <PlainTextFile> <CipherTextFile>");
            System.exit(0);
        }
        // encrypt the file
        encryptFile(generator, in, out);
    }

    /**
     * Encrypts a single plaintext character given a keystream generator
     *
     * @param plaintextChar the plaintext character to encrypt
     * @param generator the keystream generator
     * @return the calculated ciphertext character
     */
    private static char encryptChar(char plaintextChar, KeyStreamGenerator generator) {
        // get the next keystream character
        int keystreamValue = generator.getNextKeystreamValue();
        // ciphertext = (plaintext + keystream) (mod KEY_SIZE)
        int cipherTextValue = (LetterRC4Util.charToInt(plaintextChar) + keystreamValue) % LetterRC4Util.KEY_SIZE;
        return LetterRC4Util.intToChar(cipherTextValue);
    }

    /**
     * Encrypts a whole file byte-by-byte, ignoring whitespace and non-letters
     *
     * @param keystream the keystream generator
     * @param in the input stream
     * @param out the output stream
     */
    private static void encryptFile(KeyStreamGenerator keystream, FileInputStream in, FileOutputStream out) {
        int inputByte;
        char plainTextChar;
        try {
            // loop through the whole file, byte-by-byte
            while((inputByte = in.read()) != -1) {
                // interpret the input as a char
                plainTextChar = (char) inputByte;
                char cipherTextChar;
                // ignore whitespace and non-letter characters
                if(!Character.isWhitespace(plainTextChar) && Character.isLetter(plainTextChar)) {
                    cipherTextChar = encryptChar(plainTextChar, keystream);
                    out.write(Character.toUpperCase(cipherTextChar));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing file.");
            System.exit(0);
        }
    }
}


