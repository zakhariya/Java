package ua.lpr.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.lpr.dao.TaskDao;
import ua.lpr.mapper.TaskMapper;
import ua.lpr.model.Task;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String table = "tblActions", joinTable = "tblMain";

    private String columns = "act.ID, act.ActionStatus, act.ActionTitle, act.ActionNotes, act.DeleteMark, " +
                                "act.StartTime, act.EndTime, act.AddTime, act.PlaneDate, " +
                                "act.UserName, act.AddedUser, act.Priority, act.ImagePic, " +
                                "act.ClientID, cli.Client";


    @Override
    public List<Task> findAll() {
        String sql = "SELECT " + columns + " FROM " + table + " act " +
                "LEFT JOIN " + joinTable + " cli ON act.ClientID = cli.ID";

        return jdbcTemplate.query(sql, new TaskMapper());
    }

    @Override
    public List<Task> findActualByUserName(String userName) {

        String daysBefore =  LocalDate.now().minusDays(30).toString();
//        String now =  LocalDate.now().toString();
        String daysAfter = LocalDate.now().plusDays(1).toString();

        //String sql = "SELECT * FROM " + table + " WHERE UserName=? AND StartTime BETWEEN ? AND ? ORDER BY ActionStatus, Priority, StartTime";

        String sql = "SELECT " + columns + " " +
                "FROM " + table + " act " +
                "LEFT JOIN " + joinTable + " cli ON act.ClientID = cli.ID " +
                "WHERE act.UserName = ? AND act.AddTime BETWEEN ? AND ? AND act.DeleteMark = 0 " +
                "OR act.UserName = ? AND act.PlaneDate IS NOT NULL AND act.DeleteMark = 0 " +
                "ORDER BY act.ActionStatus, act.Priority, act.AddTime";

        return jdbcTemplate.query(sql, new TaskMapper(), userName, Date.valueOf(daysBefore), Date.valueOf(daysAfter), userName);
    }



    @Override
    public Task findById(long id) throws IncorrectResultSizeDataAccessException {
        String sql = "SELECT " + columns + " FROM " + table + " act " +
                "LEFT JOIN " + joinTable + " cli ON act.ClientID = cli.ID " +
                "WHERE act.ID=?";

        return jdbcTemplate.queryForObject(sql, new TaskMapper(), id);
    }

    @Override
    public Task create(Task task) throws TransientDataAccessResourceException, IncorrectResultSizeDataAccessException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO " + table + " (ActionTitle, ActionNotes, ActionStatus, AddTime," +
                " UserName, ClientID, DeleteMark, AddedUser, ImagePic, Priority) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, task.getTitle());
            ps.setString(2, task.getNotes());
            ps.setString(3, task.getStatus());
            ps.setTimestamp(4, task.getAddTime());
            ps.setString(5, task.getUserName());

            if (task.getClientId() == 0)
                ps.setString(6, null);
            else
                ps.setString(6, String.valueOf(task.getClientId()));

            ps.setBoolean(7, false);
            ps.setString(8, task.getUserName());
            ps.setBlob(9, task.getImageData());
            ps.setString(10, null);

            return ps;
        }, keyHolder);

        return jdbcTemplate.queryForObject(sql, new TaskMapper(), (long) keyHolder.getKey());
    }

    //    return "Task{" +
//            ", endTime=" + endTime +
//            ", planeTime=" + planeTime +
//            ", imageData=" + imageData +
////                ", imageBase64String=" + imageBase64String +
////                ", image=" + image +
////                ", img=" + img +
////                ", imageBytes=" + Arrays.toString(imageBytes) +
//            '}';

    @Override
    public Task update(Task task) throws TransientDataAccessResourceException, IncorrectResultSizeDataAccessException {
        String sql = "UPDATE " + table + " SET ActionTitle=?, ActionNotes=?, ActionStatus=?, EndTime=?, " +
                "UserName=?, ClientID=?, DeleteMark=?, ImagePic=?, Priority=? " +
                "WHERE id=?";

        try {
            if (task.getClientId() == 0)
                jdbcTemplate.update(sql, task.getTitle(), task.getNotes(), task.getStatus(), task.getEndTime(),
                        task.getUserName(), null, task.isDeleted(), task.getImageData(), null,
                        task.getId());
            else
                jdbcTemplate.update(sql, task.getTitle(), task.getNotes(), task.getStatus(), task.getEndTime(),
                        task.getUserName(), task.getClientId(), task.isDeleted(), task.getImageData(), null,
                        task.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        sql = "SELECT " + columns + " FROM " + table + " act " +
                "LEFT JOIN " + joinTable + " cli ON act.ClientID = cli.ID " +
                "WHERE act.ID=?";

        return jdbcTemplate.queryForObject(sql, new TaskMapper(), task.getId());
    }


/*

    @Override
    public void addTask(Task task) throws TransientDataAccessResourceException {
        try {
            String sql = "INSERT INTO " + table + " (ActionStatus, ActionTitle, ActionNotes, StartTime," +
                    " ClientID, UserName, AddTime, DeleteMark, AddedUser, ImagePic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            if (task.getClientId() == 0)
                jdbcTemplate.update(sql, task.getStatus(), task.getTitle(), task.getNotes(), task.getStartTime(), null,
                        task.getUserName(), task.getStartTime(), false, task.getUserName(), task.getImageData());
            else
                jdbcTemplate.update(sql, task.getStatus(), task.getTitle(), task.getNotes(), task.getStartTime(), task.getClientId(),
                        task.getUserName(), task.getStartTime(), false, task.getUserName(), task.getImageData());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void updateTask(Task task) throws TransientDataAccessResourceException {
        String sql = "UPDATE " + table + " SET ActionTitle=?, ActionNotes=?, ClientID=?, ImagePic=? WHERE id=?";

        if (task.getClientId() == 0)
            jdbcTemplate.update(sql, task.getTitle(), task.getNotes(), null, task.getImageData(), task.getId());
        else
            jdbcTemplate.update(sql, task.getTitle(), task.getNotes(), task.getClientId(), task.getImageData(), task.getId());
    }
*/
/*
    @Override
    public void changeStatus(Task task) {
        String sql = "UPDATE " + table + " SET ActionStatus=? WHERE id=?";

        jdbcTemplate.update(sql, task.getStatus(), task.getId());
    }

    @Override
    public void setStartTime(Task task, Timestamp timestamp) {
        String sql = "UPDATE " + table + " SET StartTime=? WHERE id=?";

        jdbcTemplate.update(sql, timestamp, task.getId());
    }

    @Override
    public void setEndTime(Task task, Timestamp timestamp) {
        String sql = "UPDATE " + table + " SET EndTime=? WHERE id=?";

        jdbcTemplate.update(sql, timestamp, task.getId());
    }

    @Override
    public InputStream get_photo_by_id(long id) throws SQLException {
        Blob blob_photo;
        String sql = "SELECT ImagePic FROM "+table+" WHERE id=?";
        blob_photo = jdbcTemplate.queryForObject(sql, new Object[] {id}, Blob.class);

        if(blob_photo != null)
            return blob_photo.getBinaryStream();
        else
            return null;
    }

    @Override
    public BufferedImage get_image_by_id(long id) throws Exception {

        String sql = "SELECT ImagePic FROM "+table+" WHERE id=?";

        Blob blob_photo = jdbcTemplate.queryForObject(sql, new Object[] {id}, Blob.class);

        InputStream is = blob_photo.getBinaryStream();

        Image image = ImageIO.read(is);

        return (BufferedImage) image;
    }
*/
}