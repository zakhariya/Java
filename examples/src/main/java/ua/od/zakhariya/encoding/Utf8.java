package ua.od.zakhariya.encoding;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Utf8 {
    public static void main(String[] args) {
        String rawString = "Entwickeln Sie mit Vergnügen";
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(rawString);

        System.out.println(buffer.get(0));
    }
}
