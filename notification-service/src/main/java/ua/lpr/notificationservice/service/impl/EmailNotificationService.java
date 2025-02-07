package ua.lpr.notificationservice.service.impl;

import org.slf4j.Logger;
import ua.lpr.notificationservice.entity.Recipient;
import ua.lpr.notificationservice.entity.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class EmailNotificationService {

    private static final Logger logger = getLogger(EmailNotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${param.email.box}")
    private String paramBox;

    @Value("${param.email.from}")
    private String paramFrom;

    @Value("${param.email.subject}")
    private String paramSubject;

    @Value("${param.email.text}")
    private String paramText;

    private ArrayList<String> emailList;

    @Async
    public void sendBroadcastMessage(Parameters parameters) {

        emailList = getEmailList(parameters.getRecipients());

        if (emailList.size() < 1) {
            logger.info("No recipients for e-mail notify.");

            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String from = parameters.getConfigValue(paramFrom) + " <" + parameters.getConfigValue(paramBox) + ">";

            helper.setFrom(from);
            helper.setSubject(parameters.getConfigValue(paramSubject));
            helper.setText(parameters.getConfigValue(paramText), true);

            for (String email : emailList) {
                try {
                    helper.setTo(email);
                    mailSender.send(message);
                    logger.info("E-mail message sent to recipient: " + email);
                } catch (MailException | MessagingException ex) {
                    logger.error("E-mail was not sent. Error: " + ex.getLocalizedMessage());
                }
            }
        } catch (MessagingException ex) {
           logger.error("E-mail was't sent. Error: " + ex.getLocalizedMessage());
        }
    }

    private ArrayList<String> getEmailList(Recipient[] recipients) {
        ArrayList<String> list = new ArrayList<>();

        for (Recipient recipient : recipients) {
            if (recipient.isActive() && recipient.isEmailNotify()
                    && recipient.getEmail() != null && recipient.getEmail().length() > 0) {
                list.add(recipient.getName() + " <" + recipient.getEmail() + ">");
            }
        }

        return list;
    }
}
