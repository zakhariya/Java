package ua.lpr.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.lpr.model.Setting;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingMapper implements RowMapper<Setting> {

    @Override
    public Setting mapRow(ResultSet resultSet, int i) throws SQLException {
        Setting setting = new Setting();

        setting.setId(resultSet.getInt("ID"));
        setting.setObject(resultSet.getString("SettObject"));
        setting.setSetting(resultSet.getString("SettParam"));
        setting.setValue(resultSet.getString("SettValue"));

        return setting;
    }
}
