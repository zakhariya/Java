package ua.lpr.dao;

import ua.lpr.model.Task;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface TaskDao {

    List<Task> findAll();

    List<Task> findActualByUserName(String userName);

    Task findById(long id);

    Task create(Task task);

    Task update(Task task);
/*
    void changeStatus(Task task);

    void setStartTime(Task task, Timestamp timestamp);

    void setEndTime(Task task, Timestamp timestamp);


    InputStream get_photo_by_id(long id) throws SQLException;

    BufferedImage get_image_by_id(long id) throws Exception;
*/
}
