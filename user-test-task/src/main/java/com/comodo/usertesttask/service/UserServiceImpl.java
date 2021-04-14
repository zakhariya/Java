package com.comodo.usertesttask.service;

import com.comodo.usertesttask.dao.UserDao;
import com.comodo.usertesttask.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    @Override
    public long getCount() {
        return userDao.getCount();
    }

    @Override
    public User getById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> listUsers(int limit, int offset) {
        return userDao.listUsers(limit, offset);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    public void deleteUser(long userId) {
        userDao.deleteUser(userId);
    }

    @Override
    public boolean userExists(User user) {
        return userDao.userExists(user);
    }
}
