package ua.od.zakhariya.system.terminal;

import ua.od.zakhariya.system.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {
    public static void main(String[] args) throws InterruptedException, IOException {
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

        // Создание процесса для Windows  cmd.exe /c ...
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "dir");
        Process process = processBuilder.start();

//            // Создание процесса для Linux, equivalent to running 'ls -l /home/user'
//            ProcessBuilder pb = new ProcessBuilder("ls", "-l", "/home/user");
//            Process process = pb.start();


//            // Executing Shell Commands (with Pipes/Redirection)
//            // Use "bash -c" to execute a complex command string
//            ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", "ls -l | grep .txt");


//            // Running Shell Scripts. Ensure the file has execution permissions.
//            // Execute a script directly
//            ProcessBuilder pb = new ProcessBuilder("./myscript.sh", "arg1", "arg2");
//
//            // Or via bash explicitly
//            ProcessBuilder pb = new ProcessBuilder("bash", "path/to/script.sh");



//            Process process = Runtime.getRuntime().exec("ping google.com");

        // Чтение вывода команды
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }

           /* Для получения данных из консоли используется process.getInputStream(),
                    а для ошибок — process.getErrorStream().
                    Для удобства часто используют Scanner или BufferedReader*/

        int exitCode = process.waitFor(); // Ожидание завершения
        System.out.println("\nКод завершения: " + exitCode);
    }
}
