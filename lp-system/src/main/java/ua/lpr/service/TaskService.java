package ua.lpr.service;

import ua.lpr.model.Task;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface TaskService {

    List<Task> findAll();

    List<Task> findHodiernalByUserName(String userName);

    Task findById(long id);

    void create(Task task);

    Task update(Task task);

//    void changeStatus(Task task);

//    void setStartTime(Task task, Timestamp timestamp);

//    void setEndTime(Task task, Timestamp timestamp);

/*
    boolean compareStatus(long taskId, String comparableStatus);

    boolean checkByTitle(long id, String title);

    boolean checkByAddedUser(long id, String addedUser);

    InputStream get_photo_by_id(long id) throws SQLException;

    BufferedImage get_image_by_id(long id) throws Exception;
*/

}
