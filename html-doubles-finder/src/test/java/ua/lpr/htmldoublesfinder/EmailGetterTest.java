/*
package ua.lpr.htmldoublesfinder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import ua.lpr.htmldoublesfinder.service.SiteService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = EmailsController.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailGetterTest {

//    @Autowired
//    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SiteService siteService;

    private List<URL> urls;

    @Value("${var.url.get.email.list}")
    private String urlGetEmailList;


    @Before
    public void setUp() throws MalformedURLException {
        urls = new ArrayList<>();

//        urls.add(new URL("https://lpr.ua/breloki"));
        urls.add(new URL("https://kushat.com.ua/"));
//        urls.add(new URL("https://cryobank.ua/services/collect/"));
//        urls.add(new URL("https://cryobank.ua/services/blood/"));
//        urls.add(new URL("https://cryobank.ua/contacts/"));
        urls.add(new URL("https://shop.agromat.ua/c_vanny"));
        urls.add(new URL("https://www.olx.ua/dom-i-sad/stroitelstvo-remont/santehnika/q-%D0%B2%D0%B0%D0%BD%D0%BD%D0%B0/"));
        urls.add(new URL("https://www.cannabisbusinessexecutive.com/2016/09/netherlands-near-legalizing-regulated-marijuana-cultivation/"));
    }

    @After
    public void tearDown() {

    }

    @Test
    public void emailsControllerGettingTest() throws Exception {
        System.out.println(this.restTemplate.postForObject("http://localhost:" + port +
                urlGetEmailList, urls, String.class));
    }

//    @Test
//    public void emailsControllerGettingTest() throws Exception {
//        assertThat(this.restTemplate.postForObject("http://localhost:" + port + urlGetEmailList, urls,
//                String.class));//.contains("Hello World");
//    }

    @Test
    public void emailsServiceGettingTest()
            throws InterruptedException, ExecutionException, URISyntaxException, IOException {

        Set<String> emailsList = siteService.getEmailsListFromPages(urls);

        for (String email : emailsList) {
            System.out.println(email);
        }
    }



//    @Test
//    public void getEmailListTest() throws Exception {
//        ResultActions resultActions = mockMvc.perform(post(urlGetEmailList, urls));
//
//        System.out.println(resultActions);
////        mockMvc.perform(post(urlGetEmailList)).andReturn().getAsyncResult().toString();
//    }

//    @Test
//    public void homePage() throws Exception {
//        // N.B. jsoup can be useful for asserting HTML content
//        mockMvc.perform(get("/index.html"))
//                .andExpect(content().string(containsString("Get your greeting")));
//    }

//    @Test
//    public void greetingWithUser() throws Exception {
//        mockMvc.perform(get("/greeting").param("name", "Greg"))
//                .andExpect(content().string(containsString("Hello, Greg!")));
//    }
}
*/
