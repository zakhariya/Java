import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class ShutdownBackupServer {

    private static final String token = "someSecurityTokenForNotificationService";
    private static final String url = "https://localhost:9393/system/";

    public static void main(String[] args) {
        ShutdownBackupServer sdbs = new ShutdownBackupServer();
        sdbs.sendRequest(url, token);
    }

    private void sendRequest(String url, String token) {
        try (CloseableHttpClient httpsClient = WebClientUtil.createHttpsClient(WebClientUtil.getTrustContext())) {
            HttpPost post = new HttpPost(url);

            System.out.println("Executing request: " + post.getRequestLine());

            HttpResponse response = httpsClient.execute(post);

            InputStream input = response.getEntity().getContent();

            String s = IOUtils.toString(input, "UTF-8");

            System.out.println(s);

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
