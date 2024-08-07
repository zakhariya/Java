package ua.od.zakhariya.system;

import java.io.IOException;

public class ShutdownSystem {
    public static void main(String arg[]) throws IOException {
//        shutdownWindows();
        shutdownWithOSCheck();
    }

    private static void shutdownWindows() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process proc = runtime.exec("shutdown -s -t 0");
        System.exit(0);
    }

    private static void shutdownWithOSCheck() throws IOException {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");

        if (operatingSystem.contains("Linux") || operatingSystem.contains("Mac OS")) {
            shutdownCommand = "shutdown -h now";
        }
        else if (operatingSystem.contains("Windows")) {
            shutdownCommand = "shutdown.exe -s -t 0";
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        Runtime.getRuntime().exec(shutdownCommand);
        System.exit(0);
    }
}
