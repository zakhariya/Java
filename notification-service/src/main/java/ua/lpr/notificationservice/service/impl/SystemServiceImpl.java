package ua.lpr.notificationservice.service.impl;

import ua.lpr.notificationservice.service.SystemService;

import java.io.IOException;

public class SystemServiceImpl implements SystemService {
    @Override
    public boolean shutdown() {
        Runtime runtime = Runtime.getRuntime();

        try{
            runtime.exec("shutdown -s -t 0");
        } catch (IOException ex) {

        }
        return false;
    }
}
