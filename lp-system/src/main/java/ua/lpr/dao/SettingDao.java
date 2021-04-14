package ua.lpr.dao;

import ua.lpr.model.Setting;

import java.util.List;

public interface SettingDao {

    Setting getById(long id);

    List<Setting> getByParam(String param);

    Setting getByObjectAndParam(String object, String param);

}
