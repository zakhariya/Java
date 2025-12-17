package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.service.NotificationService;
import ua.lpr.service.SystemService;

import java.io.IOException;

@Service
public class SystemServiceImpl implements SystemService {
    private final String OS = System.getProperty("os.name");

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean shutdownPC() {
        String command;

        if (OS.contains("Linux") || OS.contains("Mac OS")) {
            command = "shutdown -h 1"; // minutes
        }
        else if (OS.contains("Windows")) {
            command = "shutdown.exe /s /t 60"; // seconds
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        try {
            Runtime.getRuntime().exec(command);
            notificationService.notifyAdmin("System shutdown terminate in 1 minute.");
            System.out.println("System shutdown terminate in 1 minute.");

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean rebootPC() {
        String command;

        if (OS.contains("Linux") || OS.contains("Mac OS")) {
            command = "shutdown -r 1"; // minutes
        }
        else if (OS.contains("Windows")) {
            command = "shutdown.exe /r /t 60"; // seconds
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        try {
            Runtime.getRuntime().exec(command);
            notificationService.notifyAdmin("System reboot terminate in 1 minute.");
            System.out.println("System reboot terminate in 1 minute.");

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
