package ua.od.zakhariya.mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Smtp {
    private String protocol = "";

    public static void main(String[] args) {
        Smtp smtp = new Smtp();

        smtp.sendMail();
    }

    private void sendMail() {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.auth", "true");
//        props.setProperty("mail.user", login);
//        props.setProperty("mail.password", password);
//        props.put("mail.smtps.ssl.checkserveridentity", true);
        props.setProperty("mail.smtp.host", "smtp.badges.com.ua");
        props.setProperty("mail.smtp.port", "587");

        if (protocol.equalsIgnoreCase("ssl")) {
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.socketFactory.port", String.valueOf("587"));
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else if (protocol.equalsIgnoreCase("tls")) {
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.socketFactory.class", "null");
        } else {
            props.setProperty("mail.smtp.starttls.enable", "false");
            props.setProperty("mail.smtp.socketFactory.class", "null");
        }

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("info@badges.com.ua", "password");
            }
        };
        Session session = Session.getDefaultInstance(props, auth);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setSentDate(new java.util.Date());
            message.setFrom(new InternetAddress("info@badges.com.ua", "Автоматическое оповещение"));

//           if(multiMess){
//       	 	 message.addRecipients(Message.RecipientType.BCC, addresses);

            message.setSubject("Заказан звонок", "UTF-8");
            //message.setText(Engine.message);
            // message.setContent(Engine.message, "text/plain"); //multipart/alternative

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent("Заказан звонок 5445588555", "text/plain; charset=utf-8");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent("<html>" +
                    "<head>" +
                    "<title>Заказан звонок</title>" +
                    "</head>" +
                    "<body>" +
                    "<p>Заказан звонок с сайта<br>тра тат атата 5555</p>" +
                    "</body>" +
                    "</html>", "text/html; charset=utf-8");

            Transport t = session.getTransport("smtp");

            try {

                t.connect();
//				t.connect(SMTP, port , login, password);

                message.setRecipient(Message.RecipientType.TO, new InternetAddress("info@e.lpr.ua", "Человек"));

                Multipart mp = new MimeMultipart("alternative");
                mp.addBodyPart(textPart, 0);
                mp.addBodyPart(htmlPart, 1);
                message.setContent(mp);

                message.saveChanges();
                //message.setHeader("Message-ID", "112323@fdg");

                //Address recipient = new InternetAddress(email);
                t.sendMessage(message, new Address[]{new InternetAddress("info@e.lpr.ua")});

                Enumeration headers = message.getAllHeaders();

                while (headers.hasMoreElements()) {
                    Header header = (Header) headers.nextElement();
                    System.out.println(header.getName() + ": " + header.getValue());
                }

                t.close();

            } catch (Exception ex) {
                String error = ex.getLocalizedMessage();
//				if(error.indexOf("Could not connect to SMTP host:") >= 0){
//					Engine.lightFields(new JTextField[]{MainInterface.textSMTP, MainInterface.textPort});
//					Engine.progress = "Не удалось соединиться с сервером:" + Engine.smtp + " " + Engine.port;
//				}else if(error.indexOf("Authentication failed.") >= 0){
//					Engine.lightFields(new JTextField[]{MainInterface.textLogin, MainInterface.textPassword});
//					Engine.progress = "Неверные логин или пароль";
//				}else {
//					Engine.progress = error;
//				}

                ex.printStackTrace();
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
