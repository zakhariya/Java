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
        boolean encrypt = protocol.equalsIgnoreCase("ssl") || protocol.equalsIgnoreCase("tls");
        properties.setProperty("mail.smtp.starttls.enable", String.valueOf(encrypt));

        String sslClass = protocol.equalsIgnoreCase("ssl") ? "javax.net.ssl.SSLSocketFactory" : "null";

        properties.setProperty("mail.smtp.socketFactory.class", sslClass);
        properties.setProperty("mail.smtp.socketFactory.port", params.get("port"));

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


        String name = recipient.getName();
        String email = (name != null && name.length() > 0) ?
                Coder.encodeToBase64(name) + " <" + recipient.getEmail() + ">" :
                recipient.getEmail();

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

        //	msg.addHeader("List-ID", "1");
        //  msg.addHeader("List-Archive", "1");
        //  msg.setHeader("List-Post", "1");

        String url = String.format("%s?c=%s", params.get("unsubscribe"), recipient.getMd5());

        message.setHeader("List-Unsubscribe", String.format("<%s>", url));

        String text = String.format("%s%n%n???? ???????????? ???????????????????? ???????????????????? ???????????? ?? ?????? ??????????????: %s",
                params.get("text"), url);

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(text, "text/plain; charset=utf-8");

        StringBuilder html = new StringBuilder("<html><head></head><body>");
        html.append(params.get("html"));
        //TODO: change
        html.append("<br><br><p class=\"unsub\">?????????????????????? ?? ???????????????????? ???? <b><a href='");
//        html.append("<br><br>???? ???????????? ???????????????????? ?????????????? ???? <b><a href='");
        html.append(url);
        html.append("' title='List-Unsubscribe'>????????????????????</a></p></b></body></html>");
//        html.append("' title='List-Unsubscribe'>????????????</a></b></body></html>");

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

        Thread t = new Thread(() -> {
            try {
                log.info("?????????????? ???????????????? ??????????");
                controller.setProgressValue(0);
                send(session, params, recipients);
                log.info("?????????????? ???????????????? ????????????????");
                controller.setProgressValue(100);
                controller.getView().updateList(controller.getRecipientList());
                controller.stopSending();
            } catch (MessagingException | InterruptedException e) {
                String s = String.format("?????????????? ???????????????? ?????????????? ????-???? ????????????%n%s", e.getLocalizedMessage());
                log.error(s, e);
                controller.getView().updateList(controller.getRecipientList());
                controller.stopSending();
            }
        });

        new Thread(() -> {
            try {
                t.start();
                t.join();
            } catch (InterruptedException e) {
                log.error(e.getLocalizedMessage(), e);
            }
        }).start();
    }

    private void send(Session session, Map<String, String> params, List<Recipient> recipients)
            throws InterruptedException, MessagingException {

        for (int i = 0; i < recipients.size(); i++) {
            Recipient recipient = recipients.get(i);
            MimeMessage message = prepareMessage(session, params, recipient);

            long delay = Long.parseLong(params.get("delay"));

            while (delay > 0) {
                if(!isRunning) {
                    log.info("?????????????? ???????????????? ????????????????????");
                    break;
                } else {
                    sleep(1000);
                    delay -= 1000;
                }
            }

            if(!isRunning) break;

            String name = recipient.getName();
            String email = (name != null && name.length() > 0) ?
                    Coder.encodeToBase64(name) + " <" + recipient.getEmail() + ">" :
                    recipient.getEmail();
            try{
                Transport transport = session.getTransport("smtp");

                transport.connect();

                transport.sendMessage(message, new Address[] { new InternetAddress(email) });

                String s = String.format("??? %d %s ????????????????????", i + 1, recipient.getEmail());
                log.info(s);
                controller.setProgressValue((i + 1) * 100 / recipients.size());

                recipient.setSent(true);
                controller.saveRecipient(recipient);

                transport.close();
            }catch (Exception ex) {
                String s =
                        String.format("??? %d %s ???????????? ????????????????:%n%s", i + 1, recipient.getEmail(), ex.getLocalizedMessage());
                log.error(s, ex);
            }
        }
    }
}