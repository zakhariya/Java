package ua.lpr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.lpr.dao.UserDao;
import ua.lpr.mapper.UserMapper;
import ua.lpr.model.User;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    private String table = "tblUsers";

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM "+table+" ORDER BY UserName";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public List<User> getUsersByPost(String post) {
        String sql = "SELECT * FROM " + table + " WHERE Title=? ORDER BY UserName";

        return jdbcTemplate.query(sql, new UserMapper(), post);
    }

    @Override
    public List<User> getAliveUsersByPost(String post) {
        String sql = "SELECT * FROM " + table + " WHERE Title=? AND Dead=? ORDER BY UserName";

        return jdbcTemplate.query(sql, new UserMapper(), post, false);
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM "+table+" WHERE id=?";

        try{
            return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        }catch (IncorrectResultSizeDataAccessException ex){
            return null;
        }
    }

    @Override
    public User getByLogin(String userLogin) {
        String sql = "SELECT * FROM "+table+" WHERE UserName=?";

        try{
            return jdbcTemplate.queryForObject(sql, new UserMapper(), userLogin);
        }catch (IncorrectResultSizeDataAccessException ex){
            return null;
        }
    }

    @Override
    public void setLastLoginTime(User user) {
        String sql = "UPDATE " + table + " SET LastLoginTime=? WHERE ID=?";
        jdbcTemplate.update(sql, new Timestamp(Calendar.getInstance().getTimeInMillis()), user.getId());
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (name, email, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getPost());
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE "+table+" SET name=?, email=?, age=? WHERE id=?";
        jdbcTemplate.update(sql, user.getName(), user.getPost(), user.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM "+table+" WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<User> getAdminList() {
        String adminRole="Администратор";

        String sql = "SELECT * FROM "+table+" WHERE UserRole=? ORDER BY UserName";
        return jdbcTemplate.query(sql, new UserMapper(), adminRole);
    }
}
