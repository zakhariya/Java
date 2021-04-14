package ua.lpr.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.lpr.model.Workplace;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkplaceMapper implements RowMapper<Workplace> {

    public Workplace mapRow(ResultSet resultSet, int i) throws SQLException {
        Workplace workplace = new Workplace();

        workplace.setId(resultSet.getInt("ID"));
        workplace.setPartition(resultSet.getString("WorkPartition"));
        workplace.setPost(resultSet.getString("WorkPost"));

        return workplace;
    }
}
