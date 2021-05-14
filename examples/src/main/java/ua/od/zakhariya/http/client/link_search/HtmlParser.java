package ua.od.zakhariya.http.client.link_search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

    public List<String> getLinkUrls(String html) {
        List<String> urls = new ArrayList<>();

        for (Element element : getLinks(html)) {
            String attr = element.attr("href");

            if (isValidUrl(attr)) {
                urls.add(attr);
            }
        }

        return urls;
    }

    private boolean isValidUrl(String attr) {
        return !attr.contains("mailto:") && !attr.contains("tel:") && attr.contains("http");
    }

    public List<String> getImagesUrls(String html) {
        List<String> urls = new ArrayList<>();

        for (Element element : getLinks(html)) {
            urls.add(element.attr("src"));
        }

        return urls;
    }

    public List<String> getVideoUrls(String html) {
        List<String> urls = new ArrayList<>();

        for (Element element : getLinks(html)) {
            urls.add(element.select("source").first().attr("src"));
        }

        return urls;
    }

    private Elements getLinks(String html) {
        return getElementsByTag(html, "a");
    }

    private Elements getImages(String html) {
        return getElementsByTag(html, "img");
    }

    private Elements getVideos(String html) {
        return getElementsByTag(html, "video");
    }

    private Elements getElementsByTag(String html, String tag) {
        Document doc = Jsoup.parse(html).normalise();

        return doc.select(tag);
    }
}
