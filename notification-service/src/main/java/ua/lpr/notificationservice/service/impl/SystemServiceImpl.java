package ua.lpr.notificationservice.service.impl;

import org.springframework.stereotype.Service;
import ua.lpr.notificationservice.service.SystemService;

import java.io.IOException;

@Service
public class SystemServiceImpl implements SystemService {
    @Override
    public boolean shutdown() {
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
