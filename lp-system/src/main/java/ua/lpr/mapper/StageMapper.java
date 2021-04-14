package ua.lpr.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.lpr.functions.Functions;
import ua.lpr.model.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;


public class StageMapper implements RowMapper<Stage> {


    @Override
    public Stage mapRow(ResultSet resultSet, int i) throws SQLException {
        Stage stage = new Stage();

        stage.setId(resultSet.getLong("ID"));
        stage.setType(resultSet.getString("EtapName"));
        stage.setPlaneDate(resultSet.getTimestamp("Etap_Plane_date"));
        stage.setCompleteDate(resultSet.getTimestamp("Etap_fact_date"));
        stage.setOrderId(resultSet.getLong("OrderID"));
        stage.setNotes(resultSet.getString("Komment"));
        stage.setDeleted(resultSet.getBoolean("DeleteMark"));

        String userNamesFromDB = resultSet.getString("UserName");
        stage.setUserNames(Functions.fromStringToArray(userNamesFromDB, ", "));

        stage.setClientName(resultSet.getString("Client"));
        stage.setProduct(resultSet.getString("Product"));

        return stage;
    }
}
