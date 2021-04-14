/*
package ua.lpr.htmldoublesfinder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lpr.htmldoublesfinder.entity.Result;
import ua.lpr.htmldoublesfinder.service.SiteService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComparatorTest {

    @Autowired
    private SiteService comparatorService;

    private ArrayList<URL> urls;
    private ArrayList<String> selectors;

    @Before
    public void init() throws MalformedURLException {
        urls = new ArrayList<>();
        selectors = new ArrayList<>();

        urls.add(new URL("https://lpr.ua/breloki"));
        urls.add(new URL("https://lpr.ua/breloki/promo-brelki"));
        urls.add(new URL("https://lpr.ua/breloki/brelki-dlya-gostinic"));
        urls.add(new URL("https://lpr.ua/breloki/breloki-s-logotipom"));
        urls.add(new URL("https://lpr.ua/breloki/brelki-dlya-gostinic"));
        urls.add(new URL("https://lpr.ua/breloki/breloki-s-logotipom"));
        urls.add(new URL("https://lpr.ua"));

        selectors.add("head");
        selectors.add("body");
        selectors.add("p");
        selectors.add("brrrr");
        selectors.add("html");
        selectors.add("section");
        selectors.add("div");

        System.out.println("Test began");
    }

    @After
    public void tearDown() {
        System.out.println("Test finished");
    }

    @Test
    public void compareUrls() throws InterruptedException, ExecutionException, URISyntaxException, IOException {
        Result result = comparatorService.comparePages(urls,selectors);

        System.out.println(result);

        assertNotNull(result);
    }

}
*/
