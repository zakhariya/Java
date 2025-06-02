package ua.od.zakhariya.http.client;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class Post extends ResponseContent {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static String ip = "https://46.149.60.40/viber";
    private static String ip2 = "https://178.212.196.17/viber";
    private static String url = "https://api.lpr.ua/viber/message/controller.php";
    private static String url2 = "https://badges.com.ua";
    private static String url3 = "https://api.lpr.ua/test/curl/get/?a=3&b=5";
    private static String url4 = "https://chatapi.viber.com/pa/send_message";
    private static String url5 = "https://192.168.0.60:9494/print/image";
    private static final String url18 = "https://lpr.ua/bejdzhi/beidzhy-dlia-konferentsyi";
    private static final String url19 = "https://lpr.ua/ru/bejdzhi/beidzhy-dlia-konferentsyi";

    public static void main(String... args)  {
        two();
    }

    public static void one() {
        try (CloseableHttpClient httpsClient= WebClientUtil.createHttpsClient()) {
            HttpPost post = new HttpPost(url18);

            //httpget.addHeader("Host", "api.lpr.ua");
            //post.setHeader("User-Agent", USER_AGENT);
            //post.addHeader("content-type", "application/x-www-form-urlencoded");
            post.addHeader("content-type", "application/json"); //; charset=utf-8
            post.addHeader("token", "someSecurityTokenForPrintService");

            post.setEntity(new StringEntity("{\"printerName\":\"EPSON L805 Series\",\"file\":\"d:\\test_2.jpg\"}", "UTF-8"));

            System.out.println("Executing request: " + post.getRequestLine());

            long start = System.currentTimeMillis();

            HttpResponse response = httpsClient.execute(post);

            String info = "Response time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds ";

            System.out.println(info);

            JOptionPane.showMessageDialog(null, info, "", JOptionPane.INFORMATION_MESSAGE);

            InputStream input = response.getEntity().getContent();

            System.out.println("----------------------------------------\n"
                    + response.toString() + "\n\n");

//            StringWriter writer = new StringWriter();
//            IOUtils.copy(inputStream, writer, encoding);
//            String theString = writer.toString();

            String s = IOUtils.toString(input, "UTF-8");

            System.out.println(s);

            showInWindow(s);

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void two() {
        try (CloseableHttpClient httpsClient = WebClientUtil.createHttpsClient(WebClientUtil.getTrustContext())) {
            HttpPost post = new HttpPost(url19);

            System.out.println("Executing request: " + post.getRequestLine());

            HttpResponse response = httpsClient.execute(post);

            InputStream input = response.getEntity().getContent();

            String s = IOUtils.toString(input, "UTF-8");

            System.out.println(s);

//            showInWindow(s);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}