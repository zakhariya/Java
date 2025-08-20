package ua.od.zakhariya.encryption;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class JasyptEncryptor {

    private StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public JasyptEncryptor() {

        encryptor.setPassword("dajeNEHUI");
    }

    public String encryptToBase64(String text) {
        return encryptor.encrypt(text);
    }


    public String encryptProperty(String text) {
        return "ENC(" + encryptToBase64(text) + ")";
    }

    public StandardPBEStringEncryptor getEncryptor() {
        return encryptor;
    }

    public Properties loadBase64Properties(File file) throws IOException {
        Properties props = new EncryptableProperties(encryptor);

        FileInputStream in = new FileInputStream(file);

        props.load(in);
        in.close();

        return props;
    }
}
