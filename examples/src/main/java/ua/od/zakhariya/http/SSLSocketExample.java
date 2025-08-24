package ua.od.zakhariya.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class SSLSocketExample {

    public static void main(String[] args) {

        try {

//	    	// Setup the trustStore location and password
//	    	  System.setProperty("javax.net.ssl.trustStore","trust_store/cacerts.jks");
//	    	  // comment out below line
//	    	  System.setProperty("javax.net.ssl.trustStore","trust_store/keystore.jks");
//	    	  System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//	    	  //System.setProperty("javax.net.debug", "all");
//
//	    	  // for localhost testing only
//	    	  javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
//	    	        public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
//	    	          return hostname.equals("localhost");
//	    	        }
//
//	    	  });

//	    	Properties systemProps = System.getProperties();
//	        systemProps.put("javax.net.ssl.keyStorePassword","passwordForKeystore");
//	        systemProps.put("javax.net.ssl.keyStore","pathToKeystore.ks");
//	        systemProps.put("javax.net.ssl.trustStore", "pathToTruststore.ts");
//	        systemProps.put("javax.net.ssl.trustStorePassword","passwordForTrustStore");
//	        System.setProperties(systemProps);
//
//	    	HttpClient client = HttpClientBuilder.create().build();
//
//		    HttpGet request = new HttpGet("http://lpr.ua");
//
//			client.execute(request);
//
//			System.out.println("Executed");

            //    	Runtime.getRuntime().exec(new String[] {"openssl s_client -connect lpr.ua:443"});

//	    	Runtime.getRuntime().exec(new String[] {"keytool -import -alias alias.server.com -keystore $JAVA_HOME/jre/lib/security/cacerts"});

            Runtime.getRuntime().exec(new String[] {"java -Djavax.net.debug=all -Djavax.net.ssl.trustStore=trustStore"});


            String host = "lpr.ua"; //getHost("");
            Integer port = 443;//getPort("");
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);

            InputStream in = sslsocket.getInputStream();
            OutputStream out = sslsocket.getOutputStream();

            out.write(1);

            while (in.available() > 0) {
                System.out.print(in.read());
            }

            System.out.println("Secured connection performed successfully");

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}