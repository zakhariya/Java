package ua.lpr.webhelper.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.lpr.webhelper.entity.Link;
import ua.lpr.webhelper.entity.URLType;
import ua.lpr.webhelper.exception.HttpClientBadRequestException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class LinkSearcher {

    private final Map<String, Link> linkMap = new ConcurrentHashMap();
    private String domain;

    private void addLinks(String url, URLType type) {
        log.info(String.format("Executing %s", url));

        if (!url.contains(domain)) {
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new HttpClientBadRequestException().setUrl(url));

        ResponseEntity responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        int status = responseEntity.getStatusCodeValue();

        if (linkMap.containsKey(url)) {
            Link link = linkMap.get(url);
            link.setStatus(status);
        } else if (linkMap.isEmpty()) {
            Link link = new Link(url, status, type);
            linkMap.put(url, link);
        }

        HtmlParser parser = new HtmlParser();
        String html = responseEntity.getBody() == null ? "" : responseEntity.getBody().toString();

        List<String> links = parser.getPageUrls(html);

        for (String link : links) {
            if (!linkMap.containsKey(link)) {
                Link l = new Link(link, type, url);
                linkMap.put(link, l);
                addLinks(link, type);
            } else {
                linkMap.get(link).addParentPage(url);
            }
        }
    }

    public Map<String,Link> find(String url, URLType type) throws URISyntaxException {
        domain = new URI(url).getHost();

        addLinks(url, type);

        return linkMap;
    }
}
