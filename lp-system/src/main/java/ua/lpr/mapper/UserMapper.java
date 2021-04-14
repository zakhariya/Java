package ua.lpr.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.lpr.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("ID"));
        user.setName(resultSet.getString("UserName"));
        user.setPassword(resultSet.getString("UserPassword"));
        user.setPost(resultSet.getString("Title"));
        user.setEmail(resultSet.getString("UserEmail"));
        user.setViberId(resultSet.getString("ViberId"));
        user.setPhone(resultSet.getString("UserWorkPhone"));
        user.setDead(resultSet.getBoolean("Dead"));

        return user;
    }
}
