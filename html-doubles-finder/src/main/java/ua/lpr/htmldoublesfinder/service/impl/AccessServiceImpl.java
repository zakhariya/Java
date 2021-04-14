package ua.lpr.htmldoublesfinder.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.lpr.htmldoublesfinder.service.AccessService;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AccessServiceImpl implements AccessService {

    @Value("${var.app.id}")
    private String appId;

    @Override
    public boolean clientHasAccess(Set<String> urls, HttpServletRequest request) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            String serverInfo = "OS: " + System.getProperty("os.name")
                    + "\nServer name: " + InetAddress.getLocalHost().getHostName()
                    + "\nServer address: " + InetAddress.getLocalHost().getHostAddress()
                    + "\nUser name: " + System.getProperty("user.name");

            String requestInfo = "Request host: " + request.getHeader("host")
                    + "\nClient name: " + request.getLocalName()
                    + "\nClient  local address: " + request.getLocalAddr()
                    + "\nClient  remote address: " + request.getRemoteAddr()
                    + "\nClient  remote port: " + request.getRemotePort();

            Map<String, String> params = new HashMap<>();

            params.put("Application ID", appId);
            params.put("Server info", serverInfo);
            params.put("Request info", requestInfo);
            params.put("URLS", urls.toString());

            String json = new ObjectMapper().writeValueAsString(params);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

//            restTemplate.setErrorHandler(new NoOpResponseErrorHandler());

            String response = restTemplate.exchange("https://api.lpr.ua/access/",
                    HttpMethod.POST, new HttpEntity(json, headers), String.class).getBody();

            if ("Success".equals(response)) {
                return true;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

            return false;
    }
}
