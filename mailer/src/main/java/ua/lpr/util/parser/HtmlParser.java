package ua.lpr.util.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {

    public static String getTextFromHtml(String html) {
        Document doc = Jsoup.parse(html, "UTF-8").normalise();

        html = doc.body().html().replaceAll("<br>", System.lineSeparator());
        html = html.replaceAll("<hr/>", System.lineSeparator() + System.lineSeparator());
        doc.body().html(html);

        Elements bodyElements = doc.body().children();

//        return getAllTextWithWrap(bodyElements);
        return doc.body().text();
    }

    private static String getAllTextWithWrap(Elements elements) {
        StringBuilder sb = new StringBuilder();

        for (Element element : elements){
            if (element.children().size() > 0) {
                sb.append(nextLine(element));
                String text = element.text();
                String childrenText = getAllTextWithWrap(element.children());

                sb.append(text.replace(text, childrenText));
            } else {
                sb.append(nextLine(element));
                sb.append(element.text());
            }
        }

        return sb.toString();
    }

    private static String nextLine(Element element) {
        String tag = element.tagName();

        if (tag.equals("a") || tag.equals("b") || tag.equals("i")
                || tag.contains("h")) {
            return "";
        }

        return System.lineSeparator();
    }
}
