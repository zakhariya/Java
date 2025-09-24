package ua.lpr.shutdownbackup;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class ShutdownBackupServer {


    private final String JSON =
            "{\n" +
            "\"recipients\":[\n" +
            "{\n" +
            "\"id\":\"2\",\n" +
            "\"active\":\"true\",\n" +
            "\"name\":\"User\",\n" +
            "\"phone\":\"number\",\n" +
            "\"viber_id\":\"token\",\n" +
            "\"email\":\"mail@domain\",\n" +
            "\"email_notify\":\"true\",\n" +
            "\"phone_notify\":\"false\",\n" +
            "\"viber_notify\":\"true\"\n" +
            "}\n" +
            "],\n" +
            "\"configs\":[\n" +
            "{\n" +
            "\"param\":\"email_box\",\n" +
            "\"value\":\"info@domain\"\n" +
            "},{\n" +
            "\"param\":\"email_from\",\n" +
            "\"value\":\"From\"\n" +
            "},{\n" +
            "\"param\":\"sms_from\",\n" +
            "\"value\":\"Company\"\n" +
            "},{\n" +
            "\"param\":\"sms_login\",\n" +
            "\"value\":\"user\"\n" +
            "},{\n" +
            "\"param\":\"sms_password\",\n" +
            "\"value\":\"pass\"\n" +
            "},{\n" +
            "\"param\":\"sms_url\",\n" +
            "\"value\":\"https:\\/\\/gate.smsclub.mobi\\/http\\/?\"\n" +
            "},{\n" +
            "\"param\":\"timed_notification\",\n" +
            "\"value\":\"on\"\n" +
            "},{\n" +
            "\"param\":\"timed_notification_server\",\n" +
            "\"value\":\"https:\\/\\/domain:port\"\n" +
            "},{\n" +
            "\"param\":\"time_begin\",\n" +
            "\"value\":\"09:30\"\n" +
            "},{\n" +
            "\"param\":\"time_end\",\n" +
            "\"value\":\"20:00\"\n" +
            "},{\n" +
            "\"param\":\"viber_token\",\n" +
            "\"value\":\"token\"\n" +
            "},{\n" +
            "\"param\":\"viber_url\",\n" +
            "\"value\":\"https:\\/\\/chatapi.viber.com\\/pa\\/broadcast_message\"\n" +
            "},{\n" +
            "\"param\":\"email_text\",\n" +
            "\"value\":\"\\u0417\\u0430\\u043f\\u0440\\u043e\\u0441 \\u0441 \\u0441\\u0430\\u0439\\u0442\\u0430 lpr.ua<br>\\u041f\\u0440\\u043e\\u0434\\u0443\\u043a\\u0442: \\u0411\\u0435\\u0439\\u0434\\u0436\\u0438<br>\\u0418\\u043c\\u044f: \\u041c\\u0430\\u0440\\u0438\\u044fff<br>\\u0422\\u0435\\u043b\\u0435\\u0444\\u043e\\u043d: +38 (099) 999-99-99<br>e-mail: admin@lpr.ua<br>\\u0421\\u043e\\u043e\\u0431\\u0449\\u0435\\u043d\\u0438\\u0435: Some text<br>\\u0414\\u0430\\u0442\\u0430: 2019-07-04 11:55:55<br>\"\n" +
            "},{\n" +
            "\"param\":\"sms_text\",\n" +
            "\"value\":\"\\u0417\\u0430\\u043f\\u0440\\u043e\\u0441 \\u0441 \\u0441\\u0430\\u0439\\u0442\\u0430 lpr.ua \\u041f\\u0440\\u043e\\u0434\\u0443\\u043a\\u0442: \\u0411\\u0435\\u0439\\u0434\\u0436\\u0438 \\u0418\\u043c\\u044f: \\u041c\\u0430\\u0440\\u0438\\u044fff \\u0422\\u0435\\u043b\\u0435\\u0444\\u043e\\u043d: +38 (099) 999-99-99 e-mail: admin@lpr.ua \\u0421\\u043e\\u043e\\u0431\\u0449\\u0435\\u043d\\u0438\\u0435: Some text  \\u0414\\u0430\\u0442\\u0430: 2019-07-04 11:55:55\"\n" +
            "},{\n" +
            "\"param\":\"viber_text\",\n" +
            "\"value\":\"\\u0417\\u0430\\u043f\\u0440\\u043e\\u0441 \\u0441 \\u0441\\u0430\\u0439\\u0442\\u0430 lpr.ua\\\\n\\u041f\\u0440\\u043e\\u0434\\u0443\\u043a\\u0442: \\u0411\\u0435\\u0439\\u0434\\u0436\\u0438\\\\n\\u0418\\u043c\\u044f: \\u041c\\u0430\\u0440\\u0438\\u044fff\\\\n\\u0422\\u0435\\u043b\\u0435\\u0444\\u043e\\u043d: +38 (099) 999-99-99\\\\ne-mail: admin@lpr.ua\\\\n\\u0421\\u043e\\u043e\\u0431\\u0449\\u0435\\u043d\\u0438\\u0435: Some text\\\\n\\u0414\\u0430\\u0442\\u0430: 2019-07-04 11:55:55\"\n" +
            "},{\n" +
            "\"param\":\"email_title\",\n" +
            "\"value\":\"\\u0417\\u0430\\u043f\\u0440\\u043e\\u0441 \\u0441 \\u0441\\u0430\\u0439\\u0442\\u0430\"\n" +
            "}\n" +
            "]\n" +
            "}";

    public static void main(String[] args) {

        PropertiesReader properties = new PropertiesReader();

        ShutdownBackupServer sbs = new ShutdownBackupServer();

        sbs.sendRequest(properties.getUrl(), properties.getToken());


        System.out.println();

    }

    private void sendRequest(String url, String token) {
        try (CloseableHttpClient httpsClient = WebClientUtil.createHttpsClient(WebClientUtil.getTrustContext())) {
            HttpPost post = new HttpPost(url);

            post.addHeader("content-type", "application/json"); //; charset=utf-8
            post.addHeader("token", token);

            post.setEntity(new StringEntity(JSON, "UTF-8"));

            System.out.println("Executing request: " + post.getRequestLine());

            HttpResponse response = httpsClient.execute(post);

            InputStream input = response.getEntity().getContent();

            String s = IOUtils.toString(input, "UTF-8");

            System.out.println(response + " " + s);

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRequest(String url, String token, String notification) {

    }


}
