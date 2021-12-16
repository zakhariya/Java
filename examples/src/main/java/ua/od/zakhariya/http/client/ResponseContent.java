package ua.od.zakhariya.http.client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

class ResponseContent {
    private static final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

    static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
        for (int b : bytes) {
            b &= 0xff;
            sb.append(HEXDIGITS[b >> 4]);
            sb.append(HEXDIGITS[b & 15]);
            sb.append(' ');
        }
        return sb.toString();
    }

    static void showInConsole(String text) {
        System.out.println(text);
    }

    static void saveToFile(String filePath, String text) throws IOException {

//        Files.deleteIfExists(Paths.get(file));
//        Files.createFile(Paths.get(file));

        Files.write(
                Paths.get(filePath),
                text.getBytes(),
//                text.getBytes("utf-8"),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

    }

    static void showInWindow(String text) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(new JLabel("<html>" + text.replace("<!DOCTYPE html>", ""))));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
