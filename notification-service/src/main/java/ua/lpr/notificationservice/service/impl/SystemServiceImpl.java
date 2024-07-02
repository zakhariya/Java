package ua.lpr.notificationservice.service.impl;

import ua.lpr.notificationservice.service.SystemService;

import java.io.IOException;

public class SystemServiceImpl implements SystemService {
    @Override
    public boolean shutdown() {
        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");

        if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
            shutdownCommand = "shutdown -h now";
        }
        else if ("Windows".equals(operatingSystem)) {
            shutdownCommand = "shutdown.exe -s -t 0";
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        try {
            Runtime.getRuntime().exec(shutdownCommand);
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
