package ua.lpr.service;

import ua.lpr.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    List<User> getUsersByPost(String post);

    List<User> getAliveUsersByPost(String post);

    User getById(int id);

    User getByLogin(String userLogin);

    List<User> getAdminList();

    boolean validateUser(User user);

    void setLastLoginTime(User user);

    void save(User user) throws Exception;

    void update(User user);

    void delete(int id);
}
