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

public class Post {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static String ip = "https://46.149.60.40/viber";
    private static String ip2 = "https://178.212.196.17/viber";
    private static String url = "https://api.lpr.ua/viber/message/controller.php";
    private static String url2 = "https://badges.com.ua";
    private static String url3 = "https://api.lpr.ua/test/curl/get/?a=3&b=5";
    private static String url4 = "https://chatapi.viber.com/pa/send_message";
    private static String url5 = "https://192.168.0.60:9494/print/image";

    public static void main(String... args)  {

        try (CloseableHttpClient httpsClient= WebClientUtil.createHttpsClient()) {
            HttpPost post = new HttpPost(url5);

            //httpget.addHeader("Host", "api.lpr.ua");
            //post.setHeader("User-Agent", USER_AGENT);
            //post.addHeader("content-type", "application/x-www-form-urlencoded");
            post.addHeader("content-type", "application/json"); //; charset=utf-8
            //post.addHeader("Content-Type", "application/json; charset=utf-8"); //;
//    		post.addHeader("X-Viber-Auth-Token", "48e61abd0c67d43a-f10bc5336c46ec0-b541b1e1c7ee9c17");
            post.addHeader("token", "someSecurityTokenForPrintService");

            //post.setEntity(new StringEntity("{\"event\":\"get employees\",\"uid\":\"9VdfiZp2nakK0PzxMARjcg==\"}"));
            //post.setEntity(new StringEntity("{\"event\":\"get all users\",\"uid\":\"9VdfiZp2nakK0PzxMARjcg==\"}"));
//    		post.setEntity(new StringEntity("{\"receiver\":\"9VdfiZp2nakK0PzxMARjcg==\",\"type\":\"text\",\"text\":\"Добавлено задание\"}", "UTF-8"));
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

            String theString = IOUtils.toString(input, "UTF-8");

            System.out.println(theString);




            JFrame frame = new JFrame();

            frame.setLayout(new BorderLayout());
            frame.add(new JScrollPane(new JLabel("<html>" + theString.replace("<!DOCTYPE html>", ""))));

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}