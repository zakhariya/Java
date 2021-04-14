package ua.od.zakhariya.swing.combobox.search_http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class DataHttpClient {

    public List<Data> getAll() throws ClientProtocolException, IOException {

        List<Data> productNames = new ArrayList<>();

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet("http://localhost:9292/product-names");

        System.out.println("----------");
        System.out.println("Sending reques, method GET");

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();

        Header encodingHeader = entity.getContentEncoding();

        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 : Charsets.toCharset(encodingHeader.getValue());

        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        JSONArray ja = new JSONArray(json);

        int n = ja.length();

        for (int i = 0; i < n; i++) {

            JSONObject jo = ja.getJSONObject(i);

            long id = jo.getLong("id");
            String name = jo.getString("name");

            productNames.add(new Data(id, name));
        }

        System.out.println("Entities loaded and deserialized");
        System.out.println("----------");

        return productNames;
    }

    public List<Data> getByNameLike(String name) throws ClientProtocolException, IOException, URISyntaxException{

        URIBuilder builder = new URIBuilder("http://localhost:9292/product-names/find-by-name-like");
        builder.setParameter("name", name); //.setParameter("action", "finish");

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet request = new HttpGet(builder.build());

        System.out.println("----------");
        System.out.println("Sending reques, method GET");

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();

        Header encodingHeader = entity.getContentEncoding();

        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 : Charsets.toCharset(encodingHeader.getValue());

        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        JSONArray ja = new JSONArray(json);

        int n = ja.length();

        List<Data> productNames = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            JSONObject jo = ja.getJSONObject(i);

            productNames.add(new Data(jo.getLong("id"), jo.getString("name")));
        }

        System.out.println("Entities loaded and deserialized");
        System.out.println("----------");

        return productNames;
    }

    public Data save(Data productName) throws ClientProtocolException, IOException, URISyntaxException{

        HttpClient client = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost("http://localhost:9292/product-names");

        request.addHeader("content-type", "application/json");
        request.setEntity(new StringEntity("{\"id\":" + productName.getId() + ",\"name\":\"" + productName.getName() + "\"}", Charset.forName("UTF-8")));

        System.out.println("----------");
        System.out.println("Sending reques, method POST");

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        HttpEntity entity = response.getEntity();

        Header encodingHeader = entity.getContentEncoding();

        Charset encoding = encodingHeader == null ? StandardCharsets.UTF_8 : Charsets.toCharset(encodingHeader.getValue());

        String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);

        JSONObject jo = new JSONObject(json);

        productName.setId(jo.getLong("id"));
        productName.setName(jo.getString("name"));

        System.out.println("Entity loaded and deserialized");
        System.out.println("----------");

        return productName;
    }
}