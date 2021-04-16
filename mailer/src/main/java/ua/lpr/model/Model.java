package ua.lpr.model;

import com.mysql.cj.exceptions.WrongArgumentException;
import ua.lpr.dao.RecipientDao;
import ua.lpr.dao.RecipientDaoImpl;
import ua.lpr.entity.Recipient;
import ua.lpr.util.PropertiesReader;

import java.sql.SQLException;
import java.util.List;

public class Model {
    private final RecipientDao recipientDao;

    public Model() {
        PropertiesReader reader = new PropertiesReader();

        this.recipientDao = new RecipientDaoImpl(reader.getDBUrl(), reader.getDBUser(), reader.getDBPassword());
    }

    public List<Recipient> getRecipientList() throws SQLException {
        return recipientDao.findAll();
    }

    public Recipient addRecipient(Recipient recipient) throws SQLException, WrongArgumentException {
        Recipient created = recipientDao.save(recipient);

        return created;
    }

    public void delete(List<Recipient> recipients) {
        recipientDao.delete(recipients);
    }

    public void resetSent() throws SQLException {
        recipientDao.resetSent();
    }

    public void saveRecipient(Recipient recipient) throws SQLException {
        recipientDao.save(recipient);
    }
}
