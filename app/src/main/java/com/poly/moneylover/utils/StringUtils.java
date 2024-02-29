package com.poly.moneylover.utils;


import java.security.SecureRandom;

public class StringUtils {

    public static String generateRandomPassword() {
        String uppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String allCharacters = uppercaseLetters + lowercaseLetters + numbers;

        SecureRandom random = new SecureRandom();

        char firstUppercaseChar = uppercaseLetters.charAt(random.nextInt(uppercaseLetters.length()));

        char lowercaseChar = lowercaseLetters.charAt(random.nextInt(lowercaseLetters.length()));
        char numberChar = numbers.charAt(random.nextInt(numbers.length()));

        StringBuilder randomChars = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            char randomChar = allCharacters.charAt(random.nextInt(allCharacters.length()));
            randomChars.append(randomChar);
        }

        String password = "" + firstUppercaseChar + lowercaseChar + numberChar + randomChars.toString();
        return password;
    }

}
