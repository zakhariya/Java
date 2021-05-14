package ua.od.zakhariya.http.client.link_search;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ua.od.zakhariya.http.client.WebClientUtil;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LinkSearcher extends WebClientUtil {

    private static final String url = "https://lpr.ua/ru/";
    private static final String file = System.getProperty("user.dir") + "\\log\\site.html";
    private static final Map<String, Link> linkMap = new ConcurrentHashMap();
    private static String domain;
    private static Document htmlDocument = Jsoup.parse("").normalise();

    static {
        try {
            domain = new URI(url).getHost();
            htmlDocument.head().append("<style>\n" +
                    "      #content td {\n" +
                    "        max-width: 600px;\n" +
                    "        word-wrap: break-word;\n" +
                    "      }\n" +
                    "      #content td ul {\n" +
                    "          overflow: scroll;\n" +
                    "          max-height: 100px;\n" +
                    "      }\n" +
                    "      .s-200 {\n" +
                    "        color: green;\n" +
                    "      }\n" +
                    "      .s-301 {\n" +
                    "        color: blue;\n" +
                    "      }\n" +
                    "      .s-302 {\n" +
                    "        color: darkorange;\n" +
                    "      }\n" +
                    "      .s-307 {  \n" +
                    "        color: darkorange;\n" +
                    "      }\n" +
                    "      .s-404 {\n" +
                    "        color: red;\n" +
                    "      }\n" +
                    "  </style>");
            htmlDocument.body().append("<table id=\"content\" border=\"1\"><tbody></tbody></table>");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
            throws NoSuchAlgorithmException, IOException, KeyManagementException {

        addLinks(url, URLType.PAGE);

        linkMap.forEach((k, v) -> addToDocument(v));

        saveToFile(file, htmlDocument.html());
    }

    private static void addLinks(String url, URLType type)
            throws NoSuchAlgorithmException, KeyManagementException, IOException {

        if (!url.contains(domain)) {
            return;
        }

        HttpClient client = getHttpClient();
        HttpResponse response = getResponse(url, client);
        String html = getPageContent(response);
        int status = getStatusCode(response);

        if (linkMap.containsKey(url)) {
            Link link = linkMap.get(url);
            link.setStatus(status);
//            addToDocument(link);
//            saveToFile(file, String.format("%s%n", htmlDocument));
        }

        closeSocket((CloseableHttpClient) client, (CloseableHttpResponse) response);

        HtmlParser parser = new HtmlParser();
        List<String> links = parser.getLinkUrls(html);

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

    private static void addToDocument(Link link) {
        StringBuffer buffer =
                new StringBuffer(String.format("<tr class=\"s-%d\">", link.getStatus()));

        buffer.append("<td>");
        buffer.append(link.getUrl());
        buffer.append("</td>");
        buffer.append("<td>");
        buffer.append(link.getStatus());
        buffer.append("</td>");
        buffer.append("<td>");
        buffer.append(link.getType());
        buffer.append("</td>");
        buffer.append("<td>");
        buffer.append("<ul>");

        for (String parent : link.getParentPages()) {
            buffer.append("<li>");
            buffer.append(parent);
            buffer.append("</li>");
        }

        buffer.append("</ul>");
        buffer.append("</td>");

        htmlDocument.select("#content tbody").append(buffer.toString());
    }

    private static int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private static void closeSocket(CloseableHttpClient client, CloseableHttpResponse response) throws IOException {
        response.close();
        client.close();
    }

    private static HttpClient getHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
        RedirectStrategy strategy = new DefaultRedirectStrategy() {

            private List<String> methods = Arrays.asList(new String[] {
                    HttpGet.METHOD_NAME, HttpPost.METHOD_NAME, HttpHead.METHOD_NAME
            });

            @Override
            protected boolean isRedirectable(String method) {
                return false;
            }
        };

        SSLContext sslContext = WebClientUtil.getTrustContext();

        return WebClientUtil.createHttpsClient(sslContext, strategy);
    }

    private static HttpResponse getResponse(String url, HttpClient httpClient) throws IOException {
        HttpClientContext context = HttpClientContext.create();
        HttpGet get = new HttpGet(url);

        return httpClient.execute(get, context);
    }

    private static String getPageContent(HttpResponse response) throws IOException {
        InputStream input = response.getEntity().getContent();

        return IOUtils.toString(input, "UTF-8");
    }



    private static void saveToFile(String filePath, String text) throws IOException {
        Files.write(
                Paths.get(filePath),
                text.getBytes("utf-8"),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

    }
}
