package com.comodo.usertesttask.dao;

import com.comodo.usertesttask.model.User;

import java.util.Date;
import java.util.List;

public interface UserDao {

    long getCount();

    List<User> listUsers(int limit, int offset);

    User getUserById(long id);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(long userId);

    boolean userExists(User user);

}
