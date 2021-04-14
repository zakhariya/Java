/*
package ua.lpr.htmldoublesfinder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lpr.htmldoublesfinder.entity.site.Page;
import ua.lpr.htmldoublesfinder.entity.site.elements.Image;
import ua.lpr.htmldoublesfinder.entity.site.elements.Link;
import ua.lpr.htmldoublesfinder.entity.site.elements.Paragraph;
import ua.lpr.htmldoublesfinder.entity.site.impl.SinglePage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNotSame;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageTest {

    private Page page;
    private String selector = "body";


    @Before
    public void setUp() throws Exception {
        page = new SinglePage(new URL("https://lpr.ua/breloki/promo-brelki"), selector);

        System.out.println("Test began");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test finished");
    }

    @Test
    public void isSiteCreated() {
        System.out.println(page);

        assertNotNull(page);
    }

    @Test
    public void siteHasTitle() {
        String title = page.getTitle();

        assertNotNull(title);
        assertNotSame("", title);
    }

    @Test
    public void siteHasText() {
        String text = page.getText();

        System.out.println(text);
        System.out.println(text.replace(" ", "").length());

        assertNotNull(text);
        assertNotSame("", text);
    }

    @Test
    public void siteHasDomain() {
        String domain = page.getDomain();


        assertNotNull(domain);
        assertNotSame(domain, "");

        System.out.println(domain);
    }

    @Test
    public void siteHasParagraphs() {
        List<Paragraph> paragraphs = page.getParagraphs();

        for (Paragraph paragraph : paragraphs) {
            String text = paragraph.getText();
            String content = paragraph.getContent();

            System.out.println("Text: " + text + " Content: " + content);
        }

        assertNotNull(paragraphs);
    }

    @Test
    public void siteHasImages() {
        List<Image> images = page.getImages();

        for (Image image : images) {
            String src = image.getSource();
            String title = image.getTitle();
            String alt = image.getAlternativeText();
            byte[] bytes = image.getBytes();

            System.out.println("Source: " + src + " Title: " + title + " Alt: " + alt + " Bytes length: " + bytes.length);
        }

        assertNotNull(images);
        assertNotSame(0, images.size());
        assertNotSame("", images);
    }

    @Test
    public void siteHasLinks() {
        List<Link> links = page.getExternalLinks();

        for (Link link : links) {
            String href = link.getURL();
            String content = link.getContent();
            String text = link.getText();

            System.out.println("Link: " + href + " Content: " + content + " Text: " + text);
        }

        assertNotNull(links);
    }

    @Test
    public void siteHasInternalLinks() {
        List<Link> internalLinks = page.getInternalLinks();


        assertNotNull(internalLinks);

        for (Link link : internalLinks) {
            System.out.println(link.getURL() + " : " + link.getText() + " | " + link.getTitle());
        }
    }

    @Test
    public void siteHasWords() {
        List<String> bodyWords = page.getWords();

        for (String word : bodyWords) {
            System.out.println(word);
        }


        System.out.println("Size: " + bodyWords.size());

        assertNotNull(bodyWords);
    }

    @Test
    public void isSiteArraysEncapsulated() {
        List<Link> links = page.getExternalLinks();
        List<Link> internalLinks = page.getInternalLinks();
        List<Image> images = page.getImages();
        List<Paragraph> paragraphs = page.getParagraphs();

        links.add(null);
        images.add(null);
        internalLinks.add(null);
        paragraphs.add(null);

        assertNotSame(links.size(), page.getExternalLinks().size());
        assertNotSame(internalLinks.size(), page.getInternalLinks().size());
        assertNotSame(images.size(), page.getImages().size());
        assertNotSame(paragraphs.size(), page.getParagraphs().size());
    }
}
*/
