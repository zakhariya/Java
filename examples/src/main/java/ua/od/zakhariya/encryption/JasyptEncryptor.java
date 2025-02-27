package ua.od.zakhariya.encryption;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptEncryptor {

    private StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public JasyptEncryptor() {

        encryptor.setPassword("dajeNEHUI");
    }

    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }


    public String encryptProperty(String text) {
        return "ENC(" + encrypt(text) + ")";
    }

    public StandardPBEStringEncryptor getEncryptor() {
        return encryptor;
    }
}
