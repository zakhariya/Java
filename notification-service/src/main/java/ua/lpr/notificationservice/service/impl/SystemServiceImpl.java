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

    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean shutdown(Parameters parameters) {
        if (parameters != null) {
            notificationService.notifyByAll(parameters);
        }

        String shutdownCommand;
        String operatingSystem = System.getProperty("os.name");

        if (operatingSystem.contains("Linux") || operatingSystem.contains("Mac OS")) {
            shutdownCommand = "shutdown -h 1"; // minutes
        }
        else if (operatingSystem.contains("Windows")) {
            shutdownCommand = "shutdown.exe /s /t 60"; // seconds
        }
        else {
            throw new RuntimeException("Unsupported operating system.");
        }

        try {
            Runtime.getRuntime().exec(shutdownCommand);
            logger.info("System shutdown terminate in 1 minute.");

            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
