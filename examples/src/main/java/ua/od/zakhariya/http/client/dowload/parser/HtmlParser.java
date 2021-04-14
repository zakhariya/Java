package ua.od.zakhariya.http.client.dowload.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {

    public String getJsonData(String html) {
        Document doc = Jsoup.parseBodyFragment(html);
        String json = doc.getElementById("initials-script").html();

        return json
                .substring(0, json.length() - 1)
                .replace("window.initials={", "{");
    }

}
