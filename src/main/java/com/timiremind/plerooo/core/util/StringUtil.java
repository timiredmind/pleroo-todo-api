package com.timiremind.plerooo.core.util;

public class StringUtil {

    public static String maskString(String input, int numOfChar) {
        if (input.length() <= numOfChar) {
            return input;
        }
        return input.replaceAll(".(?<=..{" + numOfChar + "})", "*");
    }
}
