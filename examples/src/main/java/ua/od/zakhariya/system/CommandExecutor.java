package ua.od.zakhariya.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {
    public static void main(String[] args) {
        try {

            String command;

            if (Constants.OS.contains("Linux") || Constants.OS.contains("Mac OS")) {
                command = "shutdown -h 1"; // minutes
            }
            else if (Constants.OS.contains("Windows")) {
                command = "shutdown.exe /s /t 60"; // seconds
            }
            else {
                throw new RuntimeException("Unsupported operating system.");
            }

            // Создание процесса для Windows (для Linux/macOS: "ls", "-l")
//            cmd.exe /c ...
//            /bin/sh -c ...
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "dir");
            Process process = processBuilder.start();

//            Process process = Runtime.getRuntime().exec("ping google.com");

            // Чтение вывода команды
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

           /* Для получения данных из консоли используется process.getInputStream(),
                    а для ошибок — process.getErrorStream().
                    Для удобства часто используют Scanner или BufferedReader*/

            int exitCode = process.waitFor(); // Ожидание завершения
            System.out.println("\nКод завершения: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
