package ua.lpr.notificationservice.service;

import ua.lpr.notificationservice.entity.Parameters;

public interface NotificationService {

    void notifyEmployeesByAll(Parameters parameters);

    void notifyEmployeesBySms();

    void notifyEmployeesByEmail();

    void notifyEmployeesByViber();

}
