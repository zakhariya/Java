package ua.lpr.printservice.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Configuration
class AppConfig {

    /*
    * Not recommended at all!
    * */
    @Bean
    public Boolean disableSSLValidation() throws Exception {
        final SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        return true;
    }

//    @Bean
//    public RestTemplate setRestTemplateSSL()
//            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException  {
//
//        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
//            @Override
//            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
//                return true;
//            }
//        };
//
//        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
//                .build();
//
//        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
//        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
//
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setHttpClient(httpClient);
//
//        RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//        return restTemplate;
//
//
////        RestTemplate restTemplateSSL = new RestTemplate();
////
////        SSLContext sslContext = null;
////        try {
////            sslContext = SSLContextBuilder
////                    .create()
////                    .loadTrustMaterial(new TrustSelfSignedStrategy())
////                    .build();
////        } catch (NoSuchAlgorithmException e) {
////            e.printStackTrace();
////        } catch (KeyManagementException e) {
////            e.printStackTrace();
////        } catch (KeyStoreException e) {
////            e.printStackTrace();
////        }
////
////        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
////
////        SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
////
////        HttpClientBuilder httpClientBuilder = HttpClients.custom()
////                .setSSLSocketFactory(connectionFactory)
////                //.setSSLContext(SSLContext.getDefault())
////                .useSystemProperties();
////
////        restTemplateSSL.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build()));
////
////        return restTemplateSSL;
//    }

//    @Bean
//    public ServletWebServerFactory serverFactory() {
//        // Enable SSL Trafic
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//
//        // Add HTTP to HTTPS redirect
//        tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//
//        return tomcat;
//    }
//
//    /*
//    We need to redirect from HTTP to HTTPS. Without SSL, this application used
//    port 8082. With SSL it will use port 8443. So, any request for 8082 needs to be
//    redirected to HTTPS on 8443.
//     */
//    private Connector httpToHttpsRedirectConnector() {
//        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//        connector.setScheme("http");
//        connector.setPort(9595);
//        connector.setSecure(false);
//        connector.setRedirectPort(9494);
//
//        return connector;
//    }
}
