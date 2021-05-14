package ua.lpr.webhelper.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Link {
    private final String url;
    @Getter
    @Setter
    private int status;
    @Getter
    private final URLType type;
    @Getter
    private final Set<String> parentPages = ConcurrentHashMap.newKeySet();

    public Link(String url, URLType type, String parent) {
        this.url = url;
        this.type = type;
        addParentPage(parent);
    }

    public Link(String url, int status, URLType type) {
        this.url = url;
        this.status = status;
        this.type = type;
    }

    public String getUrl() {
        try {
            return URLDecoder.decode(url, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public synchronized void addParentPage(String url) {
        parentPages.add(url);
    }

    @Override
    public String toString() {
        try {
            return "Link{" +
                    "url='" + URLDecoder.decode(url, StandardCharsets.UTF_8.name()) + '\'' +
                    ", status=" + status +
                    ", type=" + type +
                    ", parentPages=" + parentPages +
                    '}';
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}