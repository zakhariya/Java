package ua.lpr.notificationservice.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lpr.notificationservice.entity.Parameters;
import ua.lpr.notificationservice.entity.Recipient;
import ua.lpr.notificationservice.entity.RestTemplateSSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
class ViberNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(ViberNotificationService.class);

    @Autowired
    private RestTemplateSSL restTemplateSSL;

    @Value("${param.viber.message}")
    private String paramMessage;

    @Value("${param.viber.token}")
    private String paramToken;

    @Value("${param.viber.url}")
    private String paramUrl;

    private JsonBody jsonBody;

    @Async
    public void sendBroadcastMessage(Parameters parameters) {
        HttpHeaders headers = new HttpHeaders();

        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8");
        headers.set("X-Viber-Auth-Token", parameters.getConfigValue(paramToken));

        String json = getRequestJson(parameters);

        if (!jsonBody.hasRecipients()) {
            logger.info("No recipients for viber notify.");

            return;
        }

        ResponseEntity response = restTemplateSSL.exchange(parameters.getConfigValue(paramUrl),
                HttpMethod.POST, new HttpEntity<>(json, headers), String.class);

        logger.info("Viber messages sent. Status: " + response.getStatusCode()
                + " " + response.getBody() + "\nRecipients: " + jsonBody.recipientNames);
    }

    private String getRequestJson(Parameters parameters) {
        jsonBody = new JsonBody();

        jsonBody.setType("text");
        jsonBody.setBroadcastList(parameters.getRecipients());
        jsonBody.setText(parameters.getConfigValue(paramMessage));

        ObjectMapper mapper = new ObjectMapper();
        String json = "";

        try {
            json = mapper.writeValueAsString(jsonBody);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json.replace("\\\\n", "\\n");
    }

    class JsonBody {

        private String type;
        @JsonProperty("broadcast_list")
        private ArrayList<String> broadcastList;
        private String text;
        private ArrayList<String> recipientNames;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<String> getBroadcastList() {
            return broadcastList;
        }

        public boolean hasRecipients() {
            return !broadcastList.isEmpty();
        }

        public void setBroadcastList(ArrayList<String> broadcastList) {
            this.broadcastList = broadcastList;
        }

        public void setBroadcastList(Recipient[] recipients) {
            broadcastList = new ArrayList<>();
            recipientNames = new ArrayList<>();

            for (Recipient manager : recipients) {
                if (manager.isActive() && manager.isViberNotify()
                        && manager.getViberId() != null && manager.getViberId().length() > 0) {

                    broadcastList.add(manager.getViberId());
                    recipientNames.add(manager.getName());
                }
            }
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "JsonBody{" +
                    "type='" + type + '\'' +
                    ", broadcastList=" + broadcastList +
                    ", message='" + text + '\'' +
                    '}';
        }
    }
}
