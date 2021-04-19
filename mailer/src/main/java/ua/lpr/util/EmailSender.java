package ua.lpr.util;

import org.slf4j.Logger;
import ua.lpr.controller.Controller;
import ua.lpr.entity.Recipient;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

import static java.lang.Thread.sleep;
import static org.slf4j.LoggerFactory.getLogger;

public class EmailSender {
    private final Controller controller;
    private boolean isRunning;

    private static final Logger log = getLogger(EmailSender.class);

    public EmailSender(Controller controller) {
        this.controller = controller;
    }

    public void startSending(Map<String, String> params, List<Recipient> recipients) throws MessagingException {

        Session session = createSession(params);

        isRunning = true;

        sendMessages(session, params, recipients);
    }

    public void stopSending() {
        isRunning = false;
    }

    private Session createSession(final Map<String, String> params) {
        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.host", params.get("host"));
        properties.setProperty("mail.smtp.port", params.get("port"));

        String protocol = params.get("protocol");
        boolean encrypt = protocol.equals("ssl") || protocol.equals("tls");
        properties.setProperty("mail.smtp.starttls.enable", String.valueOf(encrypt));

        String sslClass = protocol.equals("ssl") ? "javax.net.ssl.SSLSocketFactory" : "null";
        properties.setProperty("mail.smtp.socketFactory.class", sslClass);

        properties.setProperty("mail.smtp.socketFactory.port", params.get("port"));

//        if(protocol.equalsIgnoreCase("ssl")){
//
//        }else if(params.get("protocol").equalsIgnoreCase("tls")){
//
//        }else{
//
//        }

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(params.get("login"), params.get("password"));
            }
        };

        return Session.getDefaultInstance(properties, auth);
    }

    private MimeMessage prepareMessage(Session session, Map<String, String> params, Recipient recipient)
            throws MessagingException {

        MimeMessage message = new MimeMessage(session);

        String from = params.get("from");

        if (from.contains("<")) {
            String name = from.substring(0, from.indexOf("<") - 1);
            from = from.substring(from.indexOf("<"));
            from = Coder.encodeToBase64(name) + from;
        }

        message.setFrom(new InternetAddress(from));
        message.setSubject(params.get("subject"));
        message.setSentDate(new Date());

        String email = recipient.getName() != null ?
                Coder.encodeToBase64(recipient.getName()) + " <" + recipient.getEmail() + ">" :
                recipient.getEmail();

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

        //	msg.addHeader("List-ID", "1");
        //  msg.addHeader("List-Archive", "1");
        //  msg.setHeader("List-Post", "1");

        String url = String.format("%s?c=%s", params.get("unsubscribe"), recipient.getMd5());

        message.setHeader("List-Unsubscribe", String.format("<%s>", url));

        String text = String.format("%s%n%nВы можете отписаться скопировав ссылку в Ваш браузер: %s",
                params.get("text"), url);

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(text, "text/plain; charset=utf-8");

        StringBuilder html = new StringBuilder("<html><head></head><body>");
        html.append(params.get("html"));
        html.append("<br><br>Вы можете отписаться перейдя по <b><a href='");
        html.append(url);
        html.append("' title='List-Unsubscribe'>ссылке</a></b></body></html>");

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(html.toString(), "text/html; charset=utf-8");

        Multipart mp = new MimeMultipart("alternative");
        mp.addBodyPart(textPart, 0);
        mp.addBodyPart(htmlPart, 1);
        message.setContent(mp);

        message.saveChanges();

        //TODO: message-id?
//        message.setHeader("Message-ID", recipient.getMd5() + "@e.lpr.ua");

        return message;
    }

    private void sendMessages(Session session, Map<String, String> params, List<Recipient> recipients)
            throws MessagingException {

        Transport transport = session.getTransport("smtp");

        transport.connect();

        Thread t = new Thread(() -> {
            try {
                log.info("Процесс отправки начат");
                controller.setProgressValue(0);
                sendMessages(session, params, recipients, transport);
                log.info("Процесс отправки завершен");
                controller.setProgressValue(100);
                controller.getView().updateList(controller.getRecipientList());
                controller.stopSending();
            } catch (MessagingException | InterruptedException e) {
                String s = String.format("Процесс отправки прерван из-за ошибки%n%s", e.getLocalizedMessage());
                log.error(s, e);
                controller.getView().updateList(controller.getRecipientList());
                controller.stopSending();
            }
        });

        new Thread(() -> {
            try {
                t.start();
                t.join();
                transport.close();
            } catch (MessagingException | InterruptedException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }).start();
    }

    private void sendMessages(Session session, Map<String, String> params, List<Recipient> recipients, Transport t)
            throws MessagingException, InterruptedException {

        for (int i = 0; i < recipients.size(); i++) {
            Recipient recipient = recipients.get(i);
            MimeMessage message = prepareMessage(session, params, recipient);

            long delay = Long.parseLong(params.get("delay"));

            while (delay > 0) {
                if(!isRunning) {
                    log.info("Процесс отправки остановлен");
                    break;
                } else {
                    sleep(1000);
                    delay -= 1000;
                }
            }

            if(!isRunning) break;


            String email = recipient.getName() != null ?
                    Coder.encodeToBase64(recipient.getName()) + " <" + recipient.getEmail() + ">" :
                    recipient.getEmail();
            try{

                t.sendMessage(message, new Address[] { new InternetAddress(email) });

                String s = String.format("№ %d %s отправлено", i + 1, recipient.getEmail());
                log.info(s);
                controller.setProgressValue((i + 1) * 100 / recipients.size());

                recipient.setSent(true);
                controller.saveRecipient(recipient);
            }catch (Exception ex) {
                String s =
                        String.format("№ %d %s ошибка отправки:%n%s", i + 1, recipient.getEmail(), ex.getLocalizedMessage());
                log.error(s, ex);
            }
        }
    }
}