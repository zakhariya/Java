package ua.lpr.util;

import com.sun.xml.internal.ws.api.pipe.Engine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public class EmailSender {

    public void start(Map<String, String> params) throws MessagingException {

        Session session = createSession(params);
        MimeMessage mimeMessage = prepareMessage(session, params);
    }

    private Session createSession(final Map<String, String> params) {
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", params.get("smtp"));
        properties.setProperty("mail.smtp.port", params.get("port"));

        if(params.get("protocol").equalsIgnoreCase("ssl")){
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.socketFactory.port", params.get("port"));
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }else if(params.get("protocol").equalsIgnoreCase("tls")){
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.socketFactory.class", "null");
        }else{
            properties.setProperty("mail.smtp.starttls.enable", "false");
            properties.setProperty("mail.smtp.socketFactory.class", "null");
        }

        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(params.get("login"), params.get("password"));
            }
        };

        return Session.getDefaultInstance(properties, auth);
    }

    private MimeMessage prepareMessage(Session session, Map<String, String> params) throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setSentDate(new java.util.Date());
        message.setFrom(new InternetAddress(params.get("from")));


        message.setSubject(params.get("subject"));

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(params.get("messageText"), "text/plain; charset=utf-8");
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent("<html>" + params.get("messageHtml") + "</html>", "text/html; charset=utf-8");

        return message;
    }

    private void sendMessages(Session session, MimeMessage message) throws MessagingException {
        Transport t = session.getTransport("smtp");

//        Engine.progress = "Соединение с сервером";
//        Engine.writeLog(Engine.progress, true);
        t.connect();

//        String error = ex.getLocalizedMessage();
//        if(error.indexOf("Could not connect to SMTP host:") >= 0){
//            Engine.lightFields(new JTextField[]{MainInterface.textSMTP, MainInterface.textPort});
//            Engine.progress = "Не удалось соединиться с сервером:" + Engine.smtp + " " + Engine.port;
//        }else if(error.indexOf("Authentication failed.") >= 0){
//            Engine.lightFields(new JTextField[]{MainInterface.textLogin, MainInterface.textPassword});
//            Engine.progress = "Неверные логин или пароль";
//        }else {
//            Engine.progress = error;
//        }
//
//        Engine.writeLog(Engine.progress, true);
//        Engine.inProgress = false;
//
//        ex.printStackTrace();


        /*Engine.progress = "Отправка писем";
        Engine.writeLog(Engine.progress, true);
        Engine.startMillis = Calendar.getInstance().getTimeInMillis();
        int index = Engine.number;
        int size = list.getModel().getSize();

        if(Engine.delayType.equalsIgnoreCase("мин."))
            Engine.delay *= 60;

        for(int i=index; i<size; i++){

            String email = (String) list.getModel().getElementAt(i);

            try {
                Engine.progressPercent = (double) i*100/size;
                Engine.number = i;



                message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                //.addRecipient(Message.RecipientType.TO, new InternetAddress((String) list.getModel().getElementAt(i))); // TO, CC, BCC

                if(MainInterface.textURL.isEditable()){
                    String url =  Engine.generateURL(MainInterface.textURL.getText(),
                            Engine.tableName, Engine.md5Custom(email));
                    MainInterface.labURL.setText(url);
                    MainInterface.labURL.setToolTipText("Нажмите чтобы отписать: "+email);
                    //	msg.addHeader("List-ID", "1");
                    //  msg.addHeader("List-Archive", "1");
                    //  msg.setHeader("List-Post", "1");
                    message.setHeader("List-Unsubscribe", "<"+url+">");
                    textPart.setContent(Engine.message + "\n\nВы можете отписаться скопировав ссылку в Ваш браузер:\n" + url, "text/plain; charset=utf-8");
                    htmlPart.setContent("<html>" + Engine.message + "<br><br>Вы можете отписаться перейдя по <b><a href='" + url + "' title='List-Unsubscribe'>ссылке</a></b></html>", "text/html; charset=utf-8");
                }

                Multipart mp = new MimeMultipart("alternative");
                mp.addBodyPart(textPart, 0);
                mp.addBodyPart(htmlPart, 1);
                message.setContent(mp);
                for(int d=0; d<Engine.delay; d++){
                    if(!Engine.inProgress)
                        break;
                    else
                        sleep(1000);
                }
                if(!Engine.inProgress)
                    break;
                try{
//        	        	Transport.send(msg, "user", "password");
                    //Transport.send(message);

                    message.saveChanges();
                    message.setHeader("Message-ID", "112323@fdg");

                    //String[] id = message.getHeader("Message-ID");

                    //Address recipient = new InternetAddress(email);
                    t.sendMessage(message, new Address[] { new InternetAddress(email) });

                    Enumeration headers = message.getAllHeaders();
                    while (headers.hasMoreElements()) {
                        Header header = (Header) headers.nextElement();
                        System.out.println(header.getName() + ": " + header.getValue());
                    }

                    Engine.writeLog("№"+(Engine.number+1)+" "+ email + " отправлено", true);
                }catch (Exception ex) {
                    ex.printStackTrace();
                    Engine.writeLog("№"+(Engine.number+1)+" "+ email + " ошибка отправки:\n" + ex.toString(), true);
                }
            } catch (Exception ex) {
                //if(ex.getMessage().indexOf("Local address contains control or whitespace in string ``"+ email +"''") >= 0){
                //	JOptionPane.showMessageDialog(null, "неверный чпырь");
                //}

                Engine.progress = ex.toString();
                Engine.writeLog(Engine.progress, true);
                Engine.errors += Engine.progress + "\n";
                ex.printStackTrace();
            }
        }
        t.close();

        Engine.progressPercent = 100;
        Engine.progress = "Процесс отправки завершен";
        Engine.endMillis = Calendar.getInstance().getTimeInMillis();
        Engine.writeLog(Engine.progress, true);*/
    }
}
