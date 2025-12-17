package ua.lpr.dao;

import ua.lpr.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    List<User> getUsersByPost(String post);

    List<User> getAliveUsersByPost(String post);

    User getById(int id);

    User getByLogin(String userLogin);

    void setLastLoginTime(User user);

    void save(User user);

    void update(User user);

    void delete(int id);

    List<User> getAdminList();
}
