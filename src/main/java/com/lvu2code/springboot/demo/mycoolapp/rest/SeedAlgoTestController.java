package com.lvu2code.springboot.demo.mycoolapp.rest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/seed")
public class SeedAlgoTestController {

    public  static byte pbUserKey[]  = "ecplaza153620629".getBytes();
    public static byte bszIV[] = "5103370346836226".getBytes();


    @GetMapping("/encrypt")
    public String encrypt(@Param("plainText") String plainText) {

//        String plainText="203.233.213.210::20230721024155::hnin0929@ecplaza.net";

        byte pbData[]     = plainText.getBytes();

//        int PLAINTEXT_LENGTH = 14;
//        int CIPHERTEXT_LENGTH = 16;
        int PLAINTEXT_LENGTH = plainText.getBytes().length;

        //call seed encrypt algorithm
        String encodedStr = encryptWithSeed(pbData);

        return encodedStr;
    }


    @GetMapping("/decrypt")
    public String decrypt(@Param("encodedString") String encodedString) {

        //call seed decrypt algorithm
//        encodedString = "L1abI6uyk0xgcZZKmfrf+lr6eU8GqcJyodxYcOGxdYaWkmItrHPdt5Knntd9xB/Pz+5nT/246wwdK9XWNE30aw==";
        String decodedStr = decryptWithSeed(encodedString);

        return decodedStr;
    }

    @GetMapping("/bcrypt")
    public String bcrypt(@Param("password") String password) {

        // Password to hash and verify
//        String password = "mySecurePassword123";

        // Hash a password
        String hashedPassword = hashPassword(password);

        // Print the hashed password
        System.out.println("Hashed Password: " + hashedPassword);

        // Verify the password
        boolean isPasswordMatch = checkPassword("mySecurePassword123", hashedPassword);
        System.out.println("Password Match: " + isPasswordMatch);

        if(isPasswordMatch) return "Match";
        else return "Not Match";

    }

    public static String encryptWithSeed(byte[] pbData) {

        int PLAINTEXT_LENGTH = pbData.length;
        System.out.print("\n");
        System.out.print("[ Test SEED reference code CBC]"+"\n");
        System.out.print("\n\n");

        System.out.print("[ Test Encrypt mode : ��� 1 ]"+"\n");
        System.out.print("Key\t\t\t\t: ");
        for (int i=0; i<16; i++)	System.out.print(Integer.toHexString(0xff&pbUserKey[i])+" ");
        System.out.print("\n");
        System.out.print("Plaintext\t\t\t: ");
        for (int i=0; i<PLAINTEXT_LENGTH; i++)	System.out.print(Integer.toHexString(0xff&pbData[i])+" ");
        System.out.print("\n");

        byte[] defaultCipherText = KISA_SEED_CBC.SEED_CBC_Encrypt(pbUserKey, bszIV, pbData, 0, PLAINTEXT_LENGTH);

        byte[] PPPPP = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, bszIV, defaultCipherText, 0, defaultCipherText.length);

        System.out.print("\nIV\t\t\t\t: ");
        for (int i=0; i<16; i++)
            System.out.print(Integer.toHexString(0xff&bszIV[i])+" ");
        System.out.print("\n");

        System.out.print("Ciphertext(SEED_CBC_Encrypt)\t: ");
        int CIPHERTEXT_LENGTH = defaultCipherText.length;
        for (int i=0; i<CIPHERTEXT_LENGTH; i++)
            System.out.print(Integer.toHexString(0xff&defaultCipherText[i])+" ");
        System.out.print("\n");

        System.out.print("Plaintext(SEED_CBC_Decrypt)\t: ");
        for (int i=0; i<PLAINTEXT_LENGTH; i++)
            System.out.print(Integer.toHexString(0xff&PPPPP[i])+" ");
        System.out.print("\n\n");

        //base64 encode
        byte[] encodedEncryptedInfo = Base64.getEncoder().encode(defaultCipherText);
        String encodedString = new String(encodedEncryptedInfo, UTF_8);
        System.out.println(encodedString);


        return encodedString;
    }

    public static String decryptWithSeed(String encodedString){

        // Remove non-Base64 characters (e.g., whitespace, newlines)
       String cleanEncodedString = encodedString.replaceAll("\\s", "");


        // Calculate the padding required to make the input a multiple of 4 bytes
        int padding = (4 - (cleanEncodedString.length() % 4)) % 4;

        // Pad the input string with '=' as necessary
        cleanEncodedString = cleanEncodedString + "====".substring(0, padding);

        // Base64 decode the cleaned and padded string
        byte[] decodedBytes = Base64.getDecoder().decode(cleanEncodedString);


        byte[] dec = KISA_SEED_CBC.SEED_CBC_Decrypt(pbUserKey, bszIV, decodedBytes, 0, decodedBytes.length);
        String finalDecodedStr = new String(dec, UTF_8);
        System.out.println(finalDecodedStr);

        // Split the inputString by "::" and store the substrings in an array
        String[] substrings = finalDecodedStr.split("::");

        // Get the last substring after "::"
        String lastSubstring = substrings[substrings.length - 1];

        System.out.println("Last substring after '::': " + lastSubstring);

        return finalDecodedStr;
    }
    // Function to hash a password
    public static String hashPassword(String password) {
        // Define the cost factor for bcrypt (recommended between 10 and 12)
        int costFactor = 12;

        // Generate the salt and hash the password
        String salt = BCrypt.gensalt(costFactor);
        String hashedPassword = BCrypt.hashpw(password, salt);

        return hashedPassword;
    }

    // Function to check if a password matches the hashed value
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
