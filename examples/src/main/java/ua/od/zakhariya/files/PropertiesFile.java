package ua.od.zakhariya.files;

import org.jasypt.properties.EncryptableProperties;
import ua.od.zakhariya.encryption.JasyptEncryptor;
import ua.od.zakhariya.system.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

    public static void main(String[] args) {
        PropertiesFile propertiesFile = new PropertiesFile();
    }

    public PropertiesFile() {
        write(Constants.FILE, "Какой-то текст.");
        read(Constants.FILE);
    }

    private void write(File file, String text){
        Properties properties = new Properties();
        String comments = "Some comments or NULL";

        properties.setProperty("some_text_value", text);
        properties.setProperty("some_encrypted_value", new JasyptEncryptor().encryptProperty(text));

        try (FileOutputStream fos = new FileOutputStream(file)) {

            properties.store(fos, comments);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void read(File file) {
        Properties properties = new EncryptableProperties(new JasyptEncryptor().getEncryptor());

        try (FileInputStream fis = new FileInputStream(file)){

            properties.load(fis);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (String key : properties.stringPropertyNames()) {
            System.out.println(key + ": " + properties.get(key));
        }
    }
}
