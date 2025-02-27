package ua.od.zakhariya.system;

import java.io.IOException;

public class RebootShutdown {

    public static void main(String arg[]) throws IOException {
//        shutdownWithOSCheck();
//        rebootWithOSCheck();
    }

    public static void shutdownWithOSCheck() throws IOException {
        String shutdownCommand;

        if (Constants.OS.contains("Linux") || Constants.OS.contains("Mac OS")) {
            shutdownCommand = "shutdown -h now"; //"shutdown -h 1"; - minutes
        }
        else if (Constants.OS.contains("Windows")) {
            shutdownCommand = "shutdown.exe -s -t 0"; // "shutdown.exe /s /t 60"; - seconds
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        Runtime.getRuntime().exec(shutdownCommand);
        System.exit(0);
    }

    public static void rebootWithOSCheck() throws IOException {
        String command;

        if (Constants.OS.contains("Linux") || Constants.OS.contains("Mac OS")) {
            command = "shutdown -r 1"; // delay minutes
        }
        else if (Constants.OS.contains("Windows")) {
            command = "shutdown.exe /r /t 60"; // delay seconds
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        Runtime.getRuntime().exec(command);
    }
}
