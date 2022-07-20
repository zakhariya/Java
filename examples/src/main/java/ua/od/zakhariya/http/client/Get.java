package ua.od.zakhariya.http.client;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;

import javax.net.ssl.*;
import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

public class Get extends ResponseContent {

    private static final String file = System.getProperty("user.dir") + "\\log\\site.txt";

    private static final String ip = "https://46.149.60.40/viber";
    private static final String ip1 = "https://46.149.60.40";
    private static final String ip2 = "https://178.212.196.17/viber";
    private static final String ip3 = "https://178.212.196.17";

    private static final String url = "https://api.lpr.ua/viber";
    private static final String url2 = "https://api.lpr.ua";
    private static final String url3 = "https://api.lpr.ua/test/curl/get/?a=3&b=5";
    private static final String url4 = "https://badges.com.ua";
    private static final String url5 = "https://badges.com.ua/bejdzhi";
    private static final String url6 = "https://badges.com.ua///bejdzhi///beydzhi-na-magnite";
    private static final String url7 = "https://badges.com.ua/robots.txt";
    private static final String url8 = "https://lpr.ua/wp-content/uploads/2019/03/ekologichnaya-tubusnaya-upakovka-s-derevyannymi-eko-kryshkami.jpg";
    private static final String url9 = "https://lpr.ua";
    private static final String url10 = "https://eurovelo.com.ua/acsess/sholom-dytyachyj-green-cycle-foxy-50-54-pink.html";
    private static final String url11 = "https://lpr.ua/wp-content/plugins/responsive-filterable-portfolio/js/filterMediank-lbox-js.js?ver=1.0.10";
    private static final String url12 = "https://lpr.ua/about_us";
    private static final String url13 = "https://lpr.ua:9191";
    private static final String url14 = "https://kushat.com.ua/";
    private static final String url15 = "https://www.olx.ua/dom-i-sad/stroitelstvo-remont/santehnika/q-%D0%B2%D0%B0%D0%BD%D0%BD%D0%B0/";
    private static final String url16 = "https://185.104.45.157";
    private static final String url17 = "https://web.telegram.org/#/im?p=@fornod";
    private static final String url18 = "https://lpr.ua/nashi-produktyi";
    private static final String url19 = "https://lpr.ua/ru/bejdzhi/beidzhy-dlia-konferentsyi";
    private static final String url20 = "https://lpr.ua/wp-content/uploads/2014/06/img_2204.jpg";
    private static final String url21 = "https://dev.fotoservice.lpr.ua/o_nas/";
    private static final String url22 = "https://lpr.ua/zaryazhateli-magazinov-ak";

    public static void main(String... args) throws NoSuchAlgorithmException, KeyManagementException {
        three();
    }

    private static void one() throws NoSuchAlgorithmException, KeyManagementException, IOException {
        SSLContext sslContext = WebClientUtil.getTrustContext();

        URL url = new URL("https://lpr.ua/nashi-produktyi");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        System.out.println(conn.getResponseCode());
        conn.disconnect();
    }

    private static void two() {
        try (CloseableHttpClient httpclient = WebClientUtil.createHttpsClient(WebClientUtil.getTrustContext())) {
            HttpGet get = new HttpGet(url18);

//            get.addHeader("Host", "lpr.ua");

            System.out.println("Executing request: " + get.getRequestLine());

            long start = System.currentTimeMillis();

            HttpResponse response = httpclient.execute(get);

            String info = "Response time: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds ";
//            System.out.println(info);

            JOptionPane.showMessageDialog(null, info, "", JOptionPane.INFORMATION_MESSAGE);

            InputStream input = response.getEntity().getContent();
//
//            System.out.println("----------------------------------------\n"
//                    + response.toString() + "\n\n");

            System.out.println("Status code: " + response.getStatusLine().getStatusCode());

//            StringWriter writer = new StringWriter();
//            IOUtils.copy(inputStream, writer, encoding);
//            String s = writer.toString();

            String s = IOUtils.toString(input, "UTF-8");

//            showInConsole(s);

            saveToFile(file, s);

            //showInWindow(s);
        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void three() throws KeyManagementException, NoSuchAlgorithmException {

        RedirectStrategy strategy = new DefaultRedirectStrategy() {

            private final List<String> methods = Arrays.asList(HttpGet.METHOD_NAME, HttpPost.METHOD_NAME, HttpHead.METHOD_NAME);

            @Override
            protected boolean isRedirectable(String method) {
//                return methods.contains(method);

                return false;
            }
        };

        HttpClientContext context = HttpClientContext.create();
        HttpGet get = new HttpGet(url22);
        SSLContext sslContext = WebClientUtil.getTrustContext();

        try (CloseableHttpClient httpclient = WebClientUtil.createHttpsClient(sslContext, strategy);
             CloseableHttpResponse response = httpclient.execute(get, context)) {

            int status = response.getStatusLine().getStatusCode();
            System.out.println("Status code: " + status);

            if (status >= 301 && status <= 307) {
                List<URI> locations = context.getRedirectLocations();
//                locations.forEach(System.out::println);

                System.out.println(response.getHeaders("Location")[0].getValue());

            }

            for (Header header : response.getAllHeaders()) {
                System.out.println(header);
            }

            InputStream input = response.getEntity().getContent();

            String s = IOUtils.toString(input, "UTF-8");

//            showInConsole(s);

            saveToFile(file, s);

            //showInWindow(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void four() throws NoSuchAlgorithmException, KeyManagementException, IOException, KeyStoreException, CertificateException {
        String[] args = {"", ""};
        String host;
        int port;
        char[] passphrase;

        if ((args.length == 1) || (args.length == 2)) {
            String[] c = args[0].split(":");
            host = c[0];
            port = (c.length == 1) ? 443 : Integer.parseInt(c[1]);
            String p = (args.length == 1) ? "changeit" : args[1];
            passphrase = p.toCharArray();
        } else {
            System.out.println("Usage: java InstallCert [:port] [passphrase]");
            return;
        }

        File file = new File("jssecacerts");
        if (!file.isFile()) {
            char SEP = File.separatorChar;
            File dir = new File(System.getProperty("java.home") + SEP
                    + "lib" + SEP + "security");
            file = new File(dir, "jssecacerts");
            if (!file.isFile()) {
                file = new File(dir, "cacerts");
            }
        }

        System.out.println("Loading KeyStore " + file + "...");

        InputStream in = new FileInputStream(file);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(in, passphrase);
        in.close();

        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);

        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        WebClientUtil.SavingTrustManager tm = new WebClientUtil.SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory factory = context.getSocketFactory();

        System.out.println("Opening connection to " + host + ":" + port + "...");

        SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
        socket.setSoTimeout(10000);
        try {
            System.out.println("Starting SSL handshake...");
            socket.startHandshake();
            socket.close();
            System.out.println();
            System.out.println("No errors, certificate is already trusted");
        } catch (SSLException e) {
            System.out.println();
            e.printStackTrace(System.out);
        }

        X509Certificate[] chain = tm.chain;
        if (chain == null) {
            System.out.println("Could not obtain server certificate chain");
            return;
        }

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        System.out.println();
        System.out.println("Server sent " + chain.length + " certificate(s):");
        System.out.println();

        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        for (int i = 0; i < chain.length; i++) {
            X509Certificate cert = chain[i];
            System.out.println
                    (" " + (i + 1) + " Subject " + cert.getSubjectDN());
            System.out.println("   Issuer  " + cert.getIssuerDN());
            sha1.update(cert.getEncoded());
            System.out.println("   sha1    " + toHexString(sha1.digest()));
            md5.update(cert.getEncoded());
            System.out.println("   md5     " + toHexString(md5.digest()));
            System.out.println();
        }

        System.out.println("Enter certificate to add to trusted keystore or 'q' to quit: [1]");

        String line = reader.readLine().trim();
        int k;

        try {
            k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;
        } catch (NumberFormatException e) {
            System.out.println("KeyStore not changed");
            return;
        }

        X509Certificate cert = chain[k];
        String alias = host + "-" + (k + 1);
        ks.setCertificateEntry(alias, cert);

        OutputStream out = new FileOutputStream("jssecacerts");
        ks.store(out, passphrase);
        out.close();

        System.out.println();
        System.out.println(cert);
        System.out.println();
        System.out.println
                ("Added certificate to keystore 'jssecacerts' using alias '"
                        + alias + "'");
    }
}