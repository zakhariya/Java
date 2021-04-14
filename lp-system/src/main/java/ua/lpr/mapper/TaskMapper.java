package ua.lpr.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.lpr.model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {

    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();

        task.setId(resultSet.getLong("ID"));
        task.setTitle(resultSet.getString("ActionTitle"));
        task.setStartTime(resultSet.getTimestamp("StartTime"));
        task.setEndTime(resultSet.getTimestamp("EndTime"));
        task.setStatus(resultSet.getString("ActionStatus"));
        task.setNotes(resultSet.getString("ActionNotes"));
        task.setUserName(resultSet.getString("UserName"));
        task.setClientId(resultSet.getLong("ClientID"));
        task.setPlaneTime(resultSet.getTimestamp("PlaneDate"));
        task.setDeleted(resultSet.getBoolean("DeleteMark"));
        task.setAddedUser(resultSet.getString("AddedUser"));
        task.setPriority(resultSet.getInt("Priority"));
        task.setImageData(resultSet.getBlob("ImagePic"));
        task.setClientName(resultSet.getString("Client"));

        return task;
    }
}
