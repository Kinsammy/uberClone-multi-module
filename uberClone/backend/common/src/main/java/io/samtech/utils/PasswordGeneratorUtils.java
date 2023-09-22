package io.samtech.utils;

import java.security.SecureRandom;

public abstract class PasswordGeneratorUtils {

    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();

    private static final String DIGIT = "0123456789";

    private static final String OTHER_PUNCTUATION = "!@#&()–[{}]:;',?/*";

    private static final String OTHER_SYMBOL = "~$^+=<>";

    private static final String OTHER_SPECIAL = OTHER_PUNCTUATION + OTHER_SYMBOL;

    private static final String PASSWORD_ALLOW =
            CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT + OTHER_SPECIAL;

    private static final int PASSWORD_LENGTH = 20;

    private static final SecureRandom random = new SecureRandom();
    public static String generateStrongPassword() {
        StringBuilder result = new StringBuilder(PASSWORD_LENGTH);

        // at least 2 chars (lowercase)
        String strLowerCase = generateRandomString(CHAR_LOWERCASE, 2);
        result.append(strLowerCase);

        // at least 2 chars (uppercase)
        String strUppercaseCase = generateRandomString(CHAR_UPPERCASE, 2);
        result.append(strUppercaseCase);

        // at least 2 digits
        String strDigit = generateRandomString(DIGIT, 2);
        result.append(strDigit);

        // at least 2 special characters (punctuation + symbols)
        String strSpecialChar = generateRandomString(OTHER_SPECIAL, 2);
        result.append(strSpecialChar);

        // remaining, just random
        String strOther = generateRandomString(PASSWORD_ALLOW, PASSWORD_LENGTH - 8);
        result.append(strOther);

        return result.toString();

    }

    private static String generateRandomString(String input, int size) {
        if (input == null || input.length() <= 0) {
            throw new IllegalArgumentException("Invalid input string.");
        }
        if (size < 1) throw new IllegalArgumentException("Invalid size.");
        StringBuilder result = new StringBuilder(size);
        for(int index = 0; index < size; index++) {
            int randomIndex = random.nextInt(input.length());
            result.append(input.charAt(randomIndex));
        }
        return result.toString();
    }
}
