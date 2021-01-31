package com.finartz.airlinesticketsystem.util;

public class Utils {

    public static String mask = "######******####";

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

    /**
     * Applies the specified mask to the card number.
     *
     * @param cardNumber The card number in plain format
     * @param mask The number mask pattern. Use * to include a digit from the
     * card number at that position, use # to skip the digit at that position
     *
     * @return The masked card number
     */
    public static String maskCardNumber(String cardNumber, String mask) {

        // format the number
        int index = 0;
        StringBuilder maskedNumber = new StringBuilder();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c == '#') {
                maskedNumber.append(cardNumber.charAt(index));
                index++;
            } else if (c == '*') {
                maskedNumber.append(c);
                index++;
            } else {
                maskedNumber.append(c);
            }
        }

        return maskedNumber.toString();
    }
}
