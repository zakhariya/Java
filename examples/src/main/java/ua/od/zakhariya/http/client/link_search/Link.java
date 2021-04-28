package ua.od.zakhariya.http.client.link_search;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Link {
    private final String url;
    private final String status;
    private final URLType type;
    private final Set<String> pages = ConcurrentHashMap.newKeySet();

    public Link(String url, String status, URLType type) {
        this.url = url;
        this.status = status;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public URLType getType() {
        return type;
    }

    public Set<String> getPages() {
        return pages;
    }

    public synchronized void addPage(String url) {
        pages.add(url);
    }
}
