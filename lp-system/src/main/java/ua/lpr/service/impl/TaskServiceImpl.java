package ua.lpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lpr.dao.TaskDao;
import ua.lpr.model.Task;
import ua.lpr.service.TaskService;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }

    @Override
    public List<Task> findHodiernalByUserName(String userName) {
        return taskDao.findHodiernalByUserName(userName);
    }

    @Override
    public Task findById(long id) {
        return taskDao.findById(id);
    }

    @Override
    public void create(Task task) { taskDao.create(task); }

    @Override
    public Task update(Task task) {
        return taskDao.update(task);
    }

/*
    @Override
    public void setStartTime(Task task, Timestamp timestamp) { taskDao.setStartTime(task, timestamp); }

    @Override
    public void setEndTime(Task task, Timestamp timestamp) { taskDao.setEndTime(task, timestamp); }
*/
/*

    @Override
    public void changeStatus(Task task) {
        taskDao.changeStatus(task);
    }

    @Override
    public boolean compareStatus(long taskId, String comparableStatus) {
        try {
            return taskDao.getById(taskId).getStatus().equals(comparableStatus);
        }catch(NullPointerException ex){
            return false;
        }
    }

    @Override
    public boolean checkByTitle(long id, String title) {
        try {
            return taskDao.getById(id).getTitle().equals(title);
        }catch(NullPointerException ex){
            return false;
        }
    }

    @Override
    public boolean checkByAddedUser(long id, String addedUser) {
        try {
            return taskDao.getById(id).getAddedUser().equals(addedUser);
        }catch(NullPointerException ex){
            return false;
        }
    }

    @Override
    public InputStream get_photo_by_id(long id) throws SQLException {
        return taskDao.get_photo_by_id(id);
    }

    @Override
    public BufferedImage get_image_by_id(long id) throws Exception {
        return taskDao.get_image_by_id(id);
    }
*/

}
