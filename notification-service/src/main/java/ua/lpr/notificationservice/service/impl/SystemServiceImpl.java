package ua.lpr.notificationservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.notificationservice.entity.Parameters;
import ua.lpr.notificationservice.service.NotificationService;
import ua.lpr.notificationservice.service.SystemService;

import java.io.IOException;

@Service
public class SystemServiceImpl implements SystemService {

    private final String OS = System.getProperty("os.name");
    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean shutdown(Parameters parameters) {
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
            notificationService.notifyByAll(parameters);
            logger.info("System shutdown terminate in 1 minute.");

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reboot(Parameters parameters) {
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
            notificationService.notifyByAll(parameters);
            logger.info("System reboot terminate in 1 minute.");

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
