package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.dao.UserDao;
import ua.lpr.model.User;
import ua.lpr.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public List<User> getUsersByPost(String post) {
        return userDao.getUsersByPost(post);
    }

    @Override
    public List<User> getAliveUsersByPost(String post) { return userDao.getAliveUsersByPost(post); }

    @Override
    public User getById(int id) { return userDao.getById(id); }

    @Override
    public User getByLogin(String userLogin) {
        return userDao.getByLogin(userLogin);
    }

    @Override
    public List<User> getAdminList() {
        return userDao.getAdminList();
    }

    @Override
    public boolean validateUser(User user) {
        if(user == null ||
                user.getName() == null)
            return false;

        User userFromDB = getByLogin(user.getName());

        if(userFromDB != null
                && user.getPassword().equals(userFromDB.getPassword()))
            return true;

        return false;
    }

    @Override
    public void setLastLoginTime(User user) {
        userDao.setLastLoginTime(user);
    }

    @Override
    public void save(User user) throws Exception {
        if (user.getName().length() < 2) {
            throw new Exception();
        }
        userDao.save(user);
    }

    @Override
    public void update(User user) {

        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);

    }
}
