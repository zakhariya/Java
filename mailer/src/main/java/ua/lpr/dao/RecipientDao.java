package ua.lpr.dao;

import ua.lpr.entity.Recipient;

import java.sql.SQLException;
import java.util.List;

public interface RecipientDao {
    List<Recipient> findAll() throws SQLException;

    Recipient findById(int id) throws SQLException;

    Recipient save(Recipient recipient) throws SQLException;

    boolean delete(int id) throws SQLException;

    void executeSql(String sql) throws SQLException;

    void delete(List<Recipient> recipients);

    void resetSent() throws SQLException;
}
