package ua.od.zakhariya.system;

import java.io.IOException;

public class ShutdownSystem {

    public static void main(String arg[]) throws IOException {
//        shutdownWithOSCheck();
//        rebootWithOSCheck();
    }

    public static void shutdownWithOSCheck() throws IOException {
        String shutdownCommand;

        if (Constants.OS.contains("Linux") || Constants.OS.contains("Mac OS")) {
            shutdownCommand = "shutdown -h now";
        }
        else if (Constants.OS.contains("Windows")) {
            shutdownCommand = "shutdown.exe -s -t 0";
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
