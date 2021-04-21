package ua.lpr.util;

import java.util.Base64;

public class Coder {

    public static String encodeToBase64(String s) {
        s = Base64.getEncoder().encodeToString(s.getBytes());

        return "=?UTF-8?B?" + s + "?=";
    }

    public static String decode(String s) {
        byte[] decodedBytes = Base64.getDecoder().decode(s);
        return new String(decodedBytes);
    }
}
