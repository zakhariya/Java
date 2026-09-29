package ua.od.zakhariya.util;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CustomHttpClient {

    public static int simpleGet(String url) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContext sslContext = WebClientUtil.getTrustContext();

        URL realUrl = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) realUrl.openConnection();

        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });

        int code = conn.getResponseCode();

        conn.disconnect();

        return code;
    }

    private static class WebClientUtil {
        private static SSLContext getTrustContext() throws NoSuchAlgorithmException, KeyManagementException {
            // configure the SSLContext with a TrustManager
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
            SSLContext.setDefault(ctx);

            return ctx;
        }
    }

    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }
}
