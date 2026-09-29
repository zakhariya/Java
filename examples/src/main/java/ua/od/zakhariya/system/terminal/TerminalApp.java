package ua.od.zakhariya.system.terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TerminalApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Java Terminal Started. Type 'exit' to quit.");

        while (true) {
            System.out.print("> "); // Промпт
            command = scanner.nextLine();

            if ("exit".equalsIgnoreCase(command)) {
                break;
            } else if ("help".equalsIgnoreCase(command)) {
                System.out.println("Available commands: help, exit");
            } else {
                System.out.println("Unknown command: " + command);
            }

            try {
                Process process = Runtime.getRuntime().exec("cmd.exe /c" + command);
                InputStream processInputStream = process.getInputStream();

                //Java 9+
//                String result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

//                //Scanner
//                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
//                String result = s.hasNext() ? s.next() : "";

/*

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    String result = sb.toString();
                }
*/
//                //Apache Commons IO
//                String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

//                //Google Guava
//                String result = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

                
                String result = new BufferedReader(new InputStreamReader(processInputStream, StandardCharsets.UTF_8))
                        .lines().collect(Collectors.joining("\n"));

                System.out.println("Result: " + result);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }

    /*
    // Вариант 1: Использование ProcessBuilder
ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "dir");
builder.inheritIO(); // Выведет результат в консоль Java
Process p = builder.start();

// Вариант 2: Использование Runtime.exec
Process p = Runtime.getRuntime().exec("cmd.exe /c dir");


public static void main(String[] args) {
        // Укажите нужную директорию, например, текущую "."
        File folder = new File(".");

        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    System.out.println("[DIR]  " + file.getName());
                } else {
                    System.out.println("[FILE] " + file.getName());
                }
            }
        }
    }

    */
}

