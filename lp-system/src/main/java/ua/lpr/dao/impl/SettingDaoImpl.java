package ua.lpr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.lpr.dao.SettingDao;
import ua.lpr.mapper.SettingMapper;
import ua.lpr.model.Setting;

import java.util.List;

@Repository
public class SettingDaoImpl implements SettingDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private String table = "tblCustomSettings";

    @Override
    public Setting getById(long id) {
        String sql = "SELECT * FROM "+table+" WHERE ID=?";

        return jdbcTemplate.queryForObject(sql, new SettingMapper(), id);
    }

    @Override
    public Setting getByObjectAndParam(String object, String param) {
        String sql = "SELECT * FROM "+table+" WHERE SettObject=? AND SettParam=?";

        try{
            return jdbcTemplate.queryForObject(sql, new SettingMapper(), object, param);
        }catch (IncorrectResultSizeDataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Setting> getByParam(String param) {
        String sql = "SELECT * FROM "+table+" WHERE SettParam=?";

        return jdbcTemplate.query(sql, new SettingMapper(), param);
    }
}
