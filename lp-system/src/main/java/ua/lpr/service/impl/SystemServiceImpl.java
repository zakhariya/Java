package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.lpr.service.SystemService;

import java.io.IOException;

@Service
public class SystemServiceImpl implements SystemService {
    private final String OS = System.getProperty("os.name");

    @Value("${settings.shutdown.delay}")
    private int shutdownDelay; //minutes

    @Value("${settings.reboot.delay}")
    private int rebootDelay; //minutes

    @Override
    public boolean shutdownPC() {
        String command;

        if (OS.contains("Linux") || OS.contains("Mac OS")) {
            command = "shutdown -h " + shutdownDelay; // minutes
        }
        else if (OS.contains("Windows")) {
            command = "shutdown.exe /s /t " + shutdownDelay*60; // seconds
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        try {
            Runtime.getRuntime().exec(command);

            System.out.println("System shutdown terminate in " + shutdownDelay + " minute(s)");

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
            command = "shutdown -r " + rebootDelay; // minutes
        }
        else if (OS.contains("Windows")) {
            command = "shutdown.exe /r /t " + rebootDelay*60; // seconds
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        try {
            Runtime.getRuntime().exec(command);

            System.out.println("System reboot terminate in " + rebootDelay + " minute(s)");

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
