package ua.lpr.notificationservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lpr.notificationservice.entity.Recipient;
import ua.lpr.notificationservice.entity.Parameters;
import ua.lpr.notificationservice.entity.RestTemplateSSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SMSNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(SMSNotificationService.class);

    @Autowired
    private RestTemplateSSL restTemplateSSL;

    @Value("${param.sms.url}")
    private String paramUrl;

    @Value("${param.sms.login}")
    private String paramLogin;

    @Value("${param.sms.password}")
    private String paramPassword;

    @Value("${param.sms.from}")
    private String paramFrom;

    @Value("${param.sms.text}")
    private String paramText;

    private ArrayList<String> phones;

    @Async
    public void sendBroadcastMessage(Parameters parameters) {
        String xml = getRequestXmlString(parameters);

        if (phones.size() < 1) {
            logger.info("No recipients for SMS notify.");

            return;
        }

        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", MediaType.TEXT_XML_VALUE + "; charset=UTF-8");
        headers.set("Accept", MediaType.TEXT_XML_VALUE);

        ResponseEntity response = restTemplateSSL.exchange(parameters.getConfigValue(paramUrl),
                HttpMethod.POST, new HttpEntity<>(xml, headers), String.class);

        logger.info("SMS messages sent. Status: " + response.getStatusCode()
                + " " + response.getBody() + "\nPhones: " + phones);
    }

    private String getRequestXmlString(Parameters parameters) {
        Recipient[] recipients = parameters.getRecipients();
        phones = new ArrayList<>();

        for (Recipient recipient : recipients) {
            if (recipient.isActive() && recipient.isPhoneNotify()
                    && recipient.getPhone() != null && recipient.getPhone().length() > 0) {
                phones.add(recipient.getPhone());
            }
        }

        return  "<?xml version='1.0' encoding='utf-8'?>" +
                        "<request_sendsms>" +
                            "<username><![CDATA["+parameters.getConfigValue(paramLogin)+"]]></username>" +
                            "<password><![CDATA["+parameters.getConfigValue(paramPassword)+"]]></password>" +
                            "<from><![CDATA["+parameters.getConfigValue(paramFrom)+"]]></from>" +
                            "<to><![CDATA"+phones.toString().replace(", ", ";")+"]></to>" +
                            "<text><![CDATA["+parameters.getConfigValue(paramText)+"]]></text>" +
                        "</request_sendsms>";
    }
}
