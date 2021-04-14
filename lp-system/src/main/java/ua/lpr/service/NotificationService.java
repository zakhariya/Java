package ua.lpr.service;

public interface NotificationService {

    void start(UserService userService, TaskService taskService, SettingService settingService);

}
