package ua.lpr.service;

import ua.lpr.model.Setting;

import java.util.List;

public interface SettingService {

    Setting getById(long id);

    List<Setting> getByParam(String param);

    Setting getByObjectAndParam(String object, String param);
}
