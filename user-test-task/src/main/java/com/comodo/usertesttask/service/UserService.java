package com.comodo.usertesttask.service;



import com.comodo.usertesttask.model.User;

import java.util.List;


public interface UserService {

    long getCount();

    User getById(long id);

    List<User> listUsers(int limit, int offset);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(long userId);

    boolean userExists(User user);

}
