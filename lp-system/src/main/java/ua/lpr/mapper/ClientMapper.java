package ua.lpr.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.lpr.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements RowMapper<Client> {

    @Override
    public Client mapRow(ResultSet resultSet, int i) throws SQLException {
        Client client = new Client();

        client.setId(resultSet.getLong("ID"));
        client.setName(resultSet.getString("Client"));
        client.setAddress(resultSet.getString("AddressFact"));

        return client;
    }
}
