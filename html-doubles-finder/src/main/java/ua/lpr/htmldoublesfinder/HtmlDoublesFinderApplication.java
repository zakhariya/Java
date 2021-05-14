package ua.lpr.htmldoublesfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class HtmlDoublesFinderApplication {

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(100);
		executor.setQueueCapacity(500);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
//		executor.initialize();

		return executor;
	}

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

//			dfdfdf

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

	/*@Autowired
	private CloseableHttpClient httpClient;

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		return restTemplate;
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient);
		return clientHttpRequestFactory;
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("poolScheduler");
		scheduler.setPoolSize(50);
		return scheduler;
	}
*/
//	@Bean
//	public CloseableHttpClient createHttpClient() throws Exception {
//		// use the TrustSelfSignedStrategy to allow Self Signed Certificates
//		final SSLContext sslContext = SSLContextBuilder
//				.create()
//				.loadTrustMaterial(new TrustSelfSignedStrategy())
//				.build();
//
//		// we can optionally disable hostname verification.
//		// if you don't want to further weaken the security, you don't have to include this.
//		HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
//
//		// create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
//		// and allow all hosts verifier.
//		SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
//
//		// finally create the HttpClient using HttpClient factory methods and assign the ssl socket factory
//		HttpClients.custom().setSSLSocketFactory(connectionFactory).build();
//
//
//		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
//		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//			public boolean verify(String hostname, SSLSession session) {
//				return true;
//			}
//		});
//
//		return HttpClients
//				.custom()
//				.setSSLSocketFactory(connectionFactory)
//				.build();
//	}

	public static void main(String[] args) {
		SpringApplication.run(HtmlDoublesFinderApplication.class, args);
	}
}
