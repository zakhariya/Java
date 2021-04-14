/*
package ua.lpr.htmldoublesfinder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HtmlDoublesFinderApplicationTests {

	private String htmlFragment;

	private String url;

	@Before
	public void init() {
		htmlFragment = "<div class=\"page-image-cont\">\n" +
				"<div style=\"width: 279px\" class=\"wp-caption aligncenter\"><a href=\"https://lpr.ua/wp-content/uploads/2017/06/beydzh-dlya-medika-na-magnite.jpg\" class=\"fancybox image\"><img class=\"page-images wp-image-1362\" title=\"Бейджи с окном пластиковые для медиков\" src=\"https://lpr.ua/wp-content/uploads/2017/06/beydzh-dlya-medika-na-magnite-300x200.jpg\" alt=\"Бейджи с окном пластиковые для медиков\" width=\"300\" height=\"195\"><div class=\"cover\"></div></a> Бейджи с окном пластиковые для медиков\n" +
				"<div class=\"order-btn\"><a class=\"more order-btn open_modal\" title=\"Заполните форму заказа, или свяжитесь с нами\" href=\"#order-request-container\">Заказать</a></div></div>\n" +
				"<ul>\n" +
				"<li class=\"text-align-center\">\n" +
				"<p class=\"text-align:center\">Бейджи с окном пластиковые для медиков</p>\n" +
				"</li>\n" +
				"</ul>\n" +
				"<p class=\"text-align-center\"><a href=\"#order-request-container\" class=\"open_modal\">Бейджики с окошком под заказ.</a></p>\n" +
				"</div>";

		url = "https://lpr.ua/bejdzhi/bejdzhiki-plastikovye";

		System.out.println("Before");
	}

	@After
	public void print() {
		System.out.println("Test finished");
	}

	@Test
	public void fragmentHTMLTest() {
		Document doc = Jsoup.parseBodyFragment(htmlFragment);

		assertNotNull(doc);

		System.out.println(doc);
	}

	@Test
	public void requestHTMLTest() throws IOException {
		Document doc = Jsoup.connect(url).get();

		Elements elements = doc.getElementsByTag("a");

		System.out.println("Count elements: " + elements.size());

		for (Element element : elements) {
			System.out.println(element);
		}
	}

	@Test
	public void selectionTest() throws IOException {
		Connection conn = Jsoup.connect(url);

		Document doc = conn.get();

		// a with href
		Elements links = doc.select("a[href]");

		Elements links2 = doc.select("a[^data]");

		// beginning with
		Elements links3 = doc.select("a[href=^\"https://www.work]");

		// contains
		Elements links4 = doc.select("a[href=*\"work.ua]");

		// img with src ending .png
		Elements pngs = doc.select("img[src$=.jpg]");

		// div with class=masthead
		Element masthead = doc.select("div.breadcrumb").first();

		// direct a after h3 (parent-child)
		Elements resultLinks = doc.select("h3.r > a");

		Elements resultImgs = doc.select("div img");

		Elements diffEls = doc.select("div, img");
	}



	@Test
	public void yaHZ() throws IOException {
		Connection conn = Jsoup.connect(url);

		Document doc = conn.get();

		// Query <a> elements, href contain /document/
		String cssQuery = "a[href*=/document/]";
		Elements elements = doc.select(cssQuery);

		Iterator<Element> iterator = elements.iterator();

		while(iterator.hasNext())  {
			Element e = iterator.next();
			System.out.println(e.attr("href"));
		}
	}

}
*/
