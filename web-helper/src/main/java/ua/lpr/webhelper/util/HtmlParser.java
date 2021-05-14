package ua.lpr.webhelper.util;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

    public List<String> getPageUrls(String html) {
        List<String> urls = new ArrayList<>();

        for (Element element : getLinks(html)) {
            String url = element.attr("href");

            if (isValidUrl(url)) {
                urls.add(url);
            }
        }

        return urls;
    }

    private boolean isValidUrl(String url) {
        String[] schemes = {"http","https", "ftp"}; // DEFAULT schemes = "http", "https", "ftp"
        UrlValidator urlValidator = new UrlValidator(schemes);

        return urlValidator.isValid(url);
    }

    public List<String> getImagesUrls(String html) {
        List<String> urls = new ArrayList<>();

        for (Element element : getImages(html)) {
            String url = element.attr("src");

            if (isValidUrl(url)) {
                urls.add(url);
            }
        }

        return urls;
    }

    public List<String> getVideoUrls(String html) {
        List<String> urls = new ArrayList<>();

        for (Element element : getVideos(html)) {
            String url = element.select("source").first().attr("src");

            if (isValidUrl(url)) {
                urls.add(url);
            }
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
