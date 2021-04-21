package ua.lpr.util;

import ua.lpr.MailerApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private Properties properties = null;

    public PropertiesReader() {
        try (InputStream input = MailerApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getDBUrl() {
        return properties.getProperty("db.url");
    }

    public String getDBUser() {
        return properties.getProperty("db.user");
    }

    public String getDBPassword() {
        return properties.getProperty("db.password");
    }

    public String getXlsFileName() {
        return properties.getProperty("file.xls");
    }
}