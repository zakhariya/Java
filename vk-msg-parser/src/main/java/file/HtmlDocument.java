package file;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlDocument {
    private List<String> groupList = new ArrayList<>();
    private final File htmlFile;

    public HtmlDocument(File htmlFile) {
        this.htmlFile = htmlFile;
    }



    private Elements getMessageContainers() throws IOException {
        Document doc = Jsoup.parse(htmlFile, "UTF-8").normalise();

        return doc.body().getElementsByClass("message-body");
    }

    public List<String> getGroupList() {
        try {
            Elements containers = getMessageContainers();

            String senderId = containers.first().getElementsByTag("a").first().attr("href");

            fillGroupList(senderId, containers);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return groupList;
    }

    private void fillGroupList(String senderId, Elements messageContainers) {
        boolean isLastUserMsg = false;
        StringBuilder sb = new StringBuilder("- ");

        for (Element container : messageContainers) {
            try {
                String userId = container.getElementsByTag("a").first().attr("href");

                if (!userId.equalsIgnoreCase(senderId)) {
                    sb.append(container.getElementsByClass("message-text").first().text());
                    sb.append("; ");

                    isLastUserMsg = true;
                } else if (isLastUserMsg) {
                    addToGroupList(sb);

                    isLastUserMsg = false;
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        if (isLastUserMsg) {
            addToGroupList(sb);
        }
    }

    private void addToGroupList(StringBuilder stringBuilder) {
        groupList.add(stringBuilder.deleteCharAt(stringBuilder.length() - 2).toString());
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append("- ");
    }
}