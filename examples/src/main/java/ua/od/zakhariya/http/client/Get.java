package ua.od.zakhariya.http.client;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class Get {

    private static String file = "E:\\for tests\\site.txt";

    private static String ip = "https://46.149.60.40/viber";
    private static String ip1 = "https://46.149.60.40";
    private static String ip2 = "https://178.212.196.17/viber";
    private static String ip3 = "https://178.212.196.17";

    private static String url = "https://api.lpr.ua/viber";
    private static String url2 = "https://api.lpr.ua";
    private static String url3 = "https://api.lpr.ua/test/curl/get/?a=3&b=5";
    private static String url4 = "https://badges.com.ua";
    private static String url5 = "https://badges.com.ua/bejdzhi";
    private static String url6 = "https://badges.com.ua///bejdzhi///beydzhi-na-magnite";
    private static String url7 = "https://badges.com.ua/robots.txt";
    private static String url8 = "https://lpr.ua/wp-content/uploads/2019/03/ekologichnaya-tubusnaya-upakovka-s-derevyannymi-eko-kryshkami.jpg";
    private static String url9 = "https://lpr.ua";
    private static String url10 = "https://eurovelo.com.ua/acsess/sholom-dytyachyj-green-cycle-foxy-50-54-pink.html";
    private static String url11 = "https://lpr.ua/wp-content/plugins/responsive-filterable-portfolio/js/filterMediank-lbox-js.js?ver=1.0.10";
    private static String url12 = "https://lpr.ua/about_us";
    private static String url13 = "https://lpr.ua:9191";
    private static String url14 = "https://kushat.com.ua/";
    private static String url15 = "https://www.olx.ua/dom-i-sad/stroitelstvo-remont/santehnika/q-%D0%B2%D0%B0%D0%BD%D0%BD%D0%B0/";
    private static String url16 = "https://185.104.45.157";
    private static String url17 = "https://web.telegram.org/#/im?p=@fornod";

    public static void main(String... args)  {

        try (CloseableHttpClient httpclient = WebClientUtil.createHttpsClient()) {
            HttpGet httpget = new HttpGet(url17);

//            httpget.addHeader("Host", "lpr.ua");

            System.out.println("Executing request: " + httpget.getRequestLine());

            long start = System.currentTimeMillis();

            HttpResponse response = httpclient.execute(httpget);

            String info = "Response time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds ";
//            System.out.println(info);

            JOptionPane.showMessageDialog(null, info, "", JOptionPane.INFORMATION_MESSAGE);

            InputStream input = response.getEntity().getContent();
//
            System.out.println("----------------------------------------\n"
                    + response.toString() + "\n\n");

//            StringWriter writer = new StringWriter();
//            IOUtils.copy(inputStream, writer, encoding);
//            String theString = writer.toString();

            String theString = IOUtils.toString(input, "UTF-8");

            showInConsole(theString);

            saveToFile(theString);

            //showInWindow(theString);

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showInConsole(String text) {
        System.out.println(text);
    }

    private static void saveToFile(String text) throws IOException {

        //Files.createFile(Paths.get(file));

        Files.write(
                Paths.get(file),
                text.getBytes(),
//                text.getBytes("utf-8"),
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

    }

    private static void showInWindow(String text) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(new JLabel("<html>" + text.replace("<!DOCTYPE html>", ""))));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}