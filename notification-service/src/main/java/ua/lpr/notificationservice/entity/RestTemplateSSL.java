package ua.lpr.notificationservice.entity;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Component
public class RestTemplateSSL extends RestTemplate {

    public RestTemplateSSL() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContextBuilder
                    .create()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);

        HttpClientBuilder httpClientBuilder = HttpClients.custom()
                .setSSLSocketFactory(connectionFactory)
                //.setSSLContext(SSLContext.getDefault())
                .useSystemProperties();

        this.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()));
    }
}
