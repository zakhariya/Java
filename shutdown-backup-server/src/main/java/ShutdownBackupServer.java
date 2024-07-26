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

    public static void main(String[] args) {

//        ShutdownBackupServer sbs = new ShutdownBackupServer();
//        sbs.sendRequest(url, token);
    }

    private void sendRequest(String url, String token) {
        try (CloseableHttpClient httpsClient = WebClientUtil.createHttpsClient(WebClientUtil.getTrustContext())) {
            HttpPost post = new HttpPost(url);

            post.addHeader("content-type", "application/json"); //; charset=utf-8
            post.addHeader("token", token);

            post.setEntity(new StringEntity("{\"receiver\":\"9VdfiZp2nakK0PzxMARjcg==\",\"type\":\"text\",\"text\":\"Добавлено задание\"}", "UTF-8"));

            System.out.println("Executing request: " + post.getRequestLine());

            HttpResponse response = httpsClient.execute(post);

            InputStream input = response.getEntity().getContent();

            String s = IOUtils.toString(input, "UTF-8");

            System.out.println(s);

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendRequest(String url, String token, String notification) {

    }
}
