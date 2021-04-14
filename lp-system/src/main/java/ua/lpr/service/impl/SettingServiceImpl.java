package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.dao.SettingDao;
import ua.lpr.model.Setting;
import ua.lpr.service.SettingService;

import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    SettingDao settingDao;

    @Override
    public Setting getById(long id) {
        return settingDao.getById(id);
    }

    @Override
    public List<Setting> getByParam(String param) {
        return settingDao.getByParam(param);
    }

    @Override
    public Setting getByObjectAndParam(String object, String setting) {
        return settingDao.getByObjectAndParam(object, setting);
    }
}
