package ua.lpr.notificationservice.service;

import ua.lpr.notificationservice.entity.Parameters;

public interface NotificationService {

    void notifyByAll(Parameters parameters);

    void notifyBySms();

    void notifyByEmail();

    void notifyByViber();

}
