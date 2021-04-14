package ua.od.zakhariya.http.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Servlet {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws IOException {
        //sendGet();
        //sendPost();
        //sendPut();
        //sendDelete();
    }

    private static void sendGet() throws IOException {
        String url = "http://localhost:9292/products";

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet(url);

        get.addHeader("User-Agent", USER_AGENT);

        HttpResponse getResponse = client.execute(get);

        System.out.println("Response Code : "
                + getResponse.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(getResponse.getEntity().getContent()));

        StringBuffer result = new StringBuffer();

        String line = "";

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result);

    }

    private static void sendPost() throws ClientProtocolException, IOException {
        String url = "http://localhost:9292/product-names";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        post.setHeader("User-Agent", USER_AGENT);
        //post.addHeader("content-type", "application/x-www-form-urlencoded");
        post.addHeader("content-type", "application/json");
        post.setEntity(new StringEntity("{\"id\":0,\"name\":\"test\"}"));

//		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
//
//		urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
//		urlParameters.add(new BasicNameValuePair("cn", ""));
//		urlParameters.add(new BasicNameValuePair("locale", ""));
//		urlParameters.add(new BasicNameValuePair("caller", ""));
//		urlParameters.add(new BasicNameValuePair("num", "12345"));

//		post.setEntity(new UrlEncodedFormEntity(urlParameters));


        HttpResponse response = client.execute(post);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result);
    }

    private static void sendPut() throws ClientProtocolException, IOException {
        String url = "http://localhost:9292/product-names";
        int id = 12;

        HttpClient client = HttpClientBuilder.create().build();

        HttpPut httpPut = new HttpPut(url);

        httpPut.addHeader("content-type", "application/json");
        httpPut.setEntity(new StringEntity("{\"id\":" + id + ",\"name\":\"testrrrraaaaa\"}"));

        //HttpResponse response = client.execute(httpPut);

        System.out.println("Executing request " + httpPut.getRequestLine());

        // Create a custom response handler
        ResponseHandler<String> responseHandler = response -> {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        };
        String responseBody = client.execute(httpPut, responseHandler);

        System.out.println("----------------------------------------");
        System.out.println(responseBody);
    }

    private static void sendDelete() throws ClientProtocolException, IOException {
        int id = 12;
        String url = "http://localhost:9292/product-names/" + id;

//		CredentialsProvider credentials = credentialsProvider();
//		CloseableHttpClient httpclient = HttpClients.custom()
//	               .setDefaultCredentialsProvider(credentials)
//	               .build();

        HttpClient client = HttpClientBuilder.create().build();

        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader("Accept", "application/json");

        System.out.println("Executing request " + httpDelete.getRequestLine());
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpDelete);

        System.out.println("----------------------------------------");
        System.out.println((String)response.getStatusLine().getReasonPhrase());
    }
}