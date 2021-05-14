package ua.lpr.webhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executor;

@Configuration
public class AppConfig {
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("Async-");

        return executor;
    }

    @Bean
    public SSLContext disableSSLValidation() throws Exception {
        final SSLContext ctx = SSLContext.getInstance("TLS");

        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        return ctx;
    }

    private class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

//    @Bean
//    public CloseableHttpClient httpClient() throws NoSuchAlgorithmException {
//        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
//        SSLConnectionSocketFactory connectionFactory =
//                new SSLConnectionSocketFactory(SSLContext.getDefault(), allowAllHosts);
//
//        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy() {
//            @Override
//            protected boolean isRedirectable(String method) {
//                return false;
//            }
//        };
//
//        return HttpClients
//                .custom()
//                .setSSLSocketFactory(connectionFactory)
//                .setRedirectStrategy(redirectStrategy)
//                .build();
//    }
}
