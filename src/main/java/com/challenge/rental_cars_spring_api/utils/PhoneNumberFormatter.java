package com.challenge.rental_cars_spring_api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberFormatter {
    public static String formatPhoneNumber(String phoneNumber) {

        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");
        Pattern pattern = Pattern.compile("^(\\d{2})(\\d{4,5})(\\d{4})$");
        Matcher matcher = pattern.matcher(cleanedNumber);

        return matcher.matches() ? "+" + "55" + " (" + matcher.group(1) + ") " + matcher.group(2) + "-" + matcher.group(3) : phoneNumber;
    }

}
