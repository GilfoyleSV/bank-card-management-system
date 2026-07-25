package com.bank.cardmanagement.util;

import java.security.SecureRandom;

public class CardUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    private CardUtils() {
    }

    public static String maskCardNumber(String rawCardNumber) {
        if (rawCardNumber == null || rawCardNumber.length() < 4) {
            return "****";
        }
        String lastFourDigits = rawCardNumber.substring(rawCardNumber.length() - 4);
        return "**** **** **** " + lastFourDigits;
    }

    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }
}
