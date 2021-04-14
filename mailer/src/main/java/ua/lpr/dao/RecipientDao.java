package ua.lpr.dao;

import ua.lpr.entity.Recipient;

import java.sql.SQLException;
import java.util.List;

public interface RecipientDao {
    List<Recipient> findAll();

    Recipient findById(int id);

    Recipient save(Recipient recipient) throws SQLException;

    void executeSql(String sql) throws SQLException;
}
