package ua.lpr.htmldoublesfinder.entity.site.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import ua.lpr.htmldoublesfinder.HtmlDoublesFinderApplication;
import ua.lpr.htmldoublesfinder.entity.site.Page;
import ua.lpr.htmldoublesfinder.entity.site.elements.Image;
import ua.lpr.htmldoublesfinder.entity.site.elements.Link;
import ua.lpr.htmldoublesfinder.entity.site.elements.Paragraph;
import ua.lpr.htmldoublesfinder.entity.site.elements.impl.ImageImpl;
import ua.lpr.htmldoublesfinder.entity.site.elements.impl.LinkImpl;
import ua.lpr.htmldoublesfinder.entity.site.elements.impl.ParagraphImpl;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class SinglePage implements Page {

    private Logger logger = LoggerFactory.getLogger(Page.class);

    private String title;
    private String domain;
    private long wordsCount;
    private long symbolsCount;
    private String url;
    private String error;

    private List<Image> images = new ArrayList<>();
    private List<Paragraph> paragraphs = new ArrayList<>();
    private List<Link> links = new ArrayList<>();

    private List<String> filteredWords = new ArrayList<>();
    private Set<String> matchingWords = new HashSet<>();
    private Map<String, Integer> matchingWordsNumbers = new HashMap<>();

    private Document document;

    private Element textElement;

    @Value("${var.client.response.timeout}")
    private int timeout;


    public SinglePage(String url, String selector) {
        create(url, selector);
    }

    private SinglePage create(String url, String elementSelector) {
        try {
            setUrl(url);

            this.document = Jsoup.parse(getPageContent(url)).normalise();

            setTitle();
            setDomain(url);
            setParagraphs();
            setLinks();
            setImages();
            setTextElement(elementSelector);
            setFilteredWords();
            setCounts();
        } catch (Exception e) {
            setError(e, url);
        }

        return this;
    }

    @Override
    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getError() {
        return error;
    }

    private void setError(Exception e, String url) {
        error = e.toString();
        logger.error("Error on page: " + url + ":\n" + error);
    }

    private String getPageContent(String url) throws URISyntaxException, NoSuchAlgorithmException,
            KeyStoreException, KeyManagementException, IOException {

        HttpGet httpget = new HttpGet(new URI(url));

        CloseableHttpClient httpClient = createAcceptSelfSignedCertificateClient();

        HttpResponse response = httpClient.execute(httpget);


        InputStream input = response.getEntity().getContent();

        return IOUtils.toString(input, "UTF-8");
    }

    private void setTitle() {
        String title = document.title();

        if (title != null && title.length() > 0) {
            this.title = title;
        }
    }

    private void setDomain(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String host = uri.getHost();
        domain = host.startsWith("www.") ? host.substring(4) : host;
    }

    private void setLinks() {
        fillElementsListByTags(links, new String[] {"a"}, Link.class);
    }

    private void setParagraphs() {
        fillElementsListByTags(paragraphs, new String[] {"p"}, Paragraph.class);
    }

    private void setImages() {
        fillElementsListByTags(images, new String[] {"img"}, Image.class);
    }

    private void setTextElement(String selector) {
        if (selector == null) {
            textElement = document.getElementsByTag("html").first();

            return;
        }

        if (isTag(selector)) {
            textElement = document.getElementsByTag(selector).first();
        } else {
            textElement = document.getElementById(selector);
        }

        if (textElement == null) {
            textElement = document.getElementsByTag("html").first();
        }
    }

    private void setFilteredWords() {
        filteredWords = filterWords(getText());
    }

    private void setCounts() {
        for (String word : filteredWords) {
            symbolsCount += word.length();
            wordsCount++;
        }
    }

    @Override
    public void addMatchingWord(String word) {
        matchingWords.add(word);
    }
    //        return matchingWords;
    //    public Set<String> getMatchingWords() {
//    @Override

//    }

    @Override
    public Map<String, Integer> getMatchingWordsNumbers() {
        if (matchingWordsNumbers.size() == matchingWords.size()) {
            return matchingWordsNumbers;
        }

        for (String matchingWord : matchingWords) {
            int count = 0;

            for (String word : filteredWords) {
                if (matchingWord.equalsIgnoreCase(word)) {
                    count++;
                }
            }

            matchingWordsNumbers.put(matchingWord, count); //TODO: new Integer
        }

        return matchingWordsNumbers;
    }

    @Override
    public List<Image> getImages() {
        return new ArrayList<>(images);
    }

    @Override
    public String getText() {
        return textElement.text();
    }

    @Override
    public List<Paragraph> getParagraphs() {
        return new ArrayList<>(paragraphs);
    }

    @Override
    public List<Link> getExternalLinks() {
        List<Link> externalLinks = new ArrayList<>();

        for (Link link : links) {
            if (!link.getURL().toLowerCase().contains("://" + domain)
                    && !link.getURL().toLowerCase().contains("://www." + domain)) {
                externalLinks.add(link);
            }
        }

        return externalLinks;
    }

    @Override
    public List<Link> getInternalLinks() {
        List<Link> internalLinks = new ArrayList<>();

        for (Link link : links) {
            if (link.getURL().toLowerCase().contains("://" + domain)
                    || link.getURL().toLowerCase().contains("://www." + domain)) {
                internalLinks.add(link);
            }
        }

        return internalLinks;
    }

    @Override
    public List<String> getWords() {
        return filteredWords;
    }

    @Override
    public Set<String> getEmails() {
        Set<String> emails = new HashSet<>();

        for (String word : filteredWords) {
            if (isEmail(word)) {
                emails.add(word);
            }
        }

        emails.addAll(getEmailsFromMicroMarking());

        return emails;
    }

    private Set<String> getEmailsFromMicroMarking() {
        if (error != null
                && error.length() > 0) {
            return new HashSet<>();
        }

        Set<String> emails = new HashSet<>();

        Elements elements = document.getElementsByAttributeValue("type", "application/ld+json");

        for (Element element : elements) {
            List<String> words = filterWords(element.toString());

            for (String word : words) {
                if (isEmail(word)) {
                    emails.add(word);
                }
            }
        }

        return emails;
    }

    private boolean isEmail(String word) {
        if (!word.contains("@")
                || word.indexOf("@") == 0
                || word.indexOf("@") == word.length() - 1) {

            return false;
        }

        try {
            InternetAddress email = new InternetAddress(word);
            email.validate();
        } catch (AddressException ex) {
            return  false;
        }

        return true;
    }

    @Override
    public long getWordsCount() {
        return wordsCount;
    }

    @Override
    public long getSymbolsCount() {
        return symbolsCount;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    private List<String> filterWords(String text) {
        String[] words = text.replace("\n", "").split(" ");
        List<String> filteredWords = new ArrayList<>();

        char[] symbols = getWastedSymbols();

        List<String> wastedWords = getWastedWords();

        int length = words.length;

        for (int i = 0; i < length; i++) {
            if (words[i].length() <= 2) {
                continue;
            }

            for (char symbol : symbols) {
                boolean starts = true, ends = true;

                while (starts || ends) {

                    starts = words[i].startsWith(String.valueOf(symbol));
                    ends = words[i].endsWith(String.valueOf(symbol));

                    if (starts) {
                        words[i] = words[i].substring(1);
                    }

                    if (words[i].length() > 0 && ends) {
                        words[i] = words[i].substring(0, words[i].length() - 1);
                    }
                }
            }

            if (!NumberUtils.isParsable(words[i])
                    && !wastedWords.contains(words[i].toLowerCase())) {
                filteredWords.add(words[i].toLowerCase());
            }
        }

        return filteredWords;
    }

    private List<String > getWastedWords() {
        List<String> words = new ArrayList<>();

        try {
            ApplicationHome home = new ApplicationHome(HtmlDoublesFinderApplication.class);
            File file = new File(home.getDir() + File.separator + "literals/words.txt");
            words = Files.readAllLines(file.toPath());
        } catch (IOException ex) {
            logger.error(ex.getLocalizedMessage());
        }

        return words;
    }

    private char[] getWastedSymbols() {
        char[] chars = {};

        try {
//            File file = new ClassPathResource("literals/symbols.txt").getFile();
            ApplicationHome home = new ApplicationHome(HtmlDoublesFinderApplication.class);
            File file = new File(home.getDir() + File.separator + "literals/symbols.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            chars = reader.lines().collect(Collectors.joining("")).toCharArray();
        } catch (IOException ex) {
            logger.error(ex.getLocalizedMessage());
        }

        return chars;
    }

    private void fillElementsListByTags(List list, String[] tags, Class clazz) {

        list.clear();

        for (String tag : tags) {
            Elements elements = document.getElementsByTag(tag);

            if (elements.size() > 0) {
                for (Element element : elements) {
                    if (clazz.equals(Paragraph.class)) {
                        Paragraph paragraph =
                                new ParagraphImpl(element.tag(), element.baseUri(), element.attributes(), element.html());

                        list.add(paragraph);
                    } else if (clazz.equals(Image.class)) {
                        Image image =
                                new ImageImpl(element.tag(), element.baseUri(), element.attributes(), element.html());

                        list.add(image);
                    } else if (clazz.equals(Link.class)) {
                        Link link =
                                new LinkImpl(element.tag(), element.baseUri(), element.attributes(), element.html());

                        list.add(link);
                    }
                }
            }
        }
    }

    private boolean isTag(String selector) {
        //TODO: literals
        String tagList = "html head body section article div span p ul li";

        return tagList.contains(selector);
    }

    private CloseableHttpClient createAcceptSelfSignedCertificateClient() throws KeyManagementException,
            NoSuchAlgorithmException, KeyStoreException {

        SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).build();

        return HttpClients
                .custom()
                .setDefaultRequestConfig(config)
                .setSSLSocketFactory(connectionFactory)
                .build();
    }
}
