package ua.lpr.model;

import ua.lpr.dao.RecipientDao;
import ua.lpr.dao.RecipientDaoImpl;
import ua.lpr.entity.Recipient;
import ua.lpr.util.PropertiesReader;

import java.util.List;

public class Model {
    private final RecipientDao recipientDao;

    public Model() {
        PropertiesReader reader = new PropertiesReader();

        this.recipientDao = new RecipientDaoImpl(reader.getDBUrl(), reader.getDBUser(), reader.getDBPassword());
    }

    public List<Recipient> getRecipientList() {
        return recipientDao.findAll();
    }
}
