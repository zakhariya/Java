package ua.od.zakhariya.http.client.dowload;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.ID3Tag;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.MediaFile;
import org.blinkenlights.jid3.v2.ID3V2_3_0Tag;
import org.json.simple.parser.ParseException;
import ua.od.zakhariya.Constants;
import ua.od.zakhariya.http.client.dowload.parser.HtmlParser;
import ua.od.zakhariya.http.client.dowload.parser.JsonParser;

import javax.swing.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DataDownloader {
    private final String plFilePath = Constants.USER_DOWNLOADS + Constants.FILE_SEPARATOR + "index.m3u8";

    private final File playlistFile = new File(plFilePath);
    private final String urlPrefix = "https://";

    public static void main(String[] args) throws ParseException, URISyntaxException, IOException {
        DataDownloader downloader = new DataDownloader();

        String videoUrl = JOptionPane.showInputDialog(null, "Введите URL");
        URI uri = new URI(videoUrl);
        String htmlText = downloader.getHtmlText(uri);

        HtmlParser htmlParser = new HtmlParser();
        String json = htmlParser.getJsonData(htmlText);

        JsonParser jsonParser = new JsonParser();
        JsonParser.VideoInfo videoInfo = jsonParser.getVideoInfo(json);

        String playLists = downloader.getHtmlText(new URI(downloader.urlPrefix + uri.getHost() + videoInfo.getPlaylistUri()));
        List<URI> playlistUrls = downloader.getUrlsFromLines(new StringReader(playLists));
        URI hqPlaylistUrl = downloader.getHQPlaylistUrl(playlistUrls);
        String playListData = downloader.getPlayListData(hqPlaylistUrl);

        List<URI> urls = downloader.getUrlsFromLines(new StringReader(playListData));
        downloader.save(videoInfo, urls);

//        downloader.readFile();

//        downloader.downloadFromPlayList();
    }

    private void readFile() {
        File f = new File(Constants.USER_DOWNLOADS + Constants.FILE_SEPARATOR + "2_1_HW1.mp4");
        MediaFile mediaFile;

        mediaFile = new MP3File(f);

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            try {
                ID3V2_3_0Tag tag = new ID3V2_3_0Tag();
//                tag.setArtist("Ryan Higdon");
//                tag.setAlbum("Ryan's Funky Beats");
//                mediaFile.setID3Tag(tag);
//                mediaFile.sync();

                for (ID3Tag t : mediaFile.getTags()) {
                    System.out.println(t.toString());
                }
//
//                for (ID3Tag eachTag : mediaFile.getTags()) {
//                    System.out.println(eachTag.toString());
//                }

            } catch (ID3Exception e) {
                e.printStackTrace();
                System.out.println("something bad happened");
            }
//            catch (ID3Exception e) {
//                e.printStackTrace();
//            }

//            br.lines().forEach(System.out::println);
//                    .filter(string -> string.startsWith(urlPrefix))
//                    .map(s -> {
//                        try {
//                            return new URI(s);
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//
//                            return null;
//                        }
//                    })
//                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPlayListData(URI hqPlaylistUrl) throws IOException {
        String s = null;
        try (CloseableHttpClient httpclient = HttpUtil.createAcceptSelfSignedCertificateClient()) {
            HttpGet httpget = new HttpGet(hqPlaylistUrl);

            String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
            String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 OPR/74.0.3911.107 (Edition Yx 03)";

            httpget.addHeader("accept", accept);
            httpget.addHeader("user-agent", agent);

            HttpResponse response = response = httpclient.execute(httpget);

            s = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }

        return s;
    }

    private void save(JsonParser.VideoInfo videoInfo, List<URI> urls) throws IOException {
        try (CloseableHttpClient httpclient = HttpUtil.createAcceptSelfSignedCertificateClient()) {
            HttpGet httpget = new HttpGet();

            String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
            String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 OPR/74.0.3911.107 (Edition Yx 03)";

            httpget.addHeader("accept", accept);
            httpget.addHeader("user-agent", agent);

            HttpResponse response = null;

            File file = new File(Constants.USER_DOWNLOADS + videoInfo.getTitle() + ".mp4");
            OutputStream outStream = new FileOutputStream(file);

            for (URI url : urls) {
                httpget.setURI(url);

                response = httpclient.execute(httpget);
                response.getEntity().writeTo(outStream);
            }

            outStream.flush();
            outStream.close();

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private void save(JsonParser.VideoInfo videoInfo, URI uri) throws IOException {
        try (CloseableHttpClient httpclient = HttpUtil.createAcceptSelfSignedCertificateClient()) {
            HttpGet httpget = new HttpGet(uri);

            String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
            String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 OPR/74.0.3911.107 (Edition Yx 03)";

            httpget.addHeader("accept", accept);
            httpget.addHeader("user-agent", agent);

            HttpResponse response = null;

            File file = new File(Constants.USER_DOWNLOADS + videoInfo.getTitle() + ".mp4");
            OutputStream outStream = new FileOutputStream(file);

            response = httpclient.execute(httpget);
            response.getEntity().writeTo(outStream);

            outStream.flush();
            outStream.close();

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadFromPlayList(File playlistFile) throws IOException {
        List<URI> urls = getUrlsFromLines(new FileReader(playlistFile));

        try (CloseableHttpClient httpclient = HttpUtil.createAcceptSelfSignedCertificateClient()) {
            HttpGet httpget = new HttpGet();

            String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
            String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 OPR/74.0.3911.107 (Edition Yx 03)";

            httpget.addHeader("accept", accept);
            httpget.addHeader("user-agent", agent);

            HttpResponse response = null;
            OutputStream outStream = new FileOutputStream(new File(""));

            for (URI url : urls) {
                httpget.setURI(url);

                response = httpclient.execute(httpget);
                response.getEntity().writeTo(outStream);
            }

            outStream.flush();
            outStream.close();

        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private URI getHQPlaylistUrl(List<URI> playLists) {
        int maxQuality = 0;
        URI uri = null;

        for (URI u : playLists) {
            int q = getQuality(u.toString());

            if (q > maxQuality) {
                maxQuality = q;
                uri = u;
            }
        }

        return uri;
    }

    private int getQuality(String s) {
        final Pattern pattern = Pattern.compile("\\/\\d{0,4}p\\.", Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(s);

        matcher.find();
        String res = matcher.group(0);

        return Integer.parseInt(res.substring(1, res.length() - 2));
    }

    private String getHtmlText(URI url) throws IOException {
        try (CloseableHttpClient httpclient = HttpUtil.createAcceptSelfSignedCertificateClient()) {
            HttpGet httpget = new HttpGet(url);

            String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9";
            String agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36 OPR/74.0.3911.107 (Edition Yx 03)";

            httpget.addHeader("accept", accept);
            httpget.addHeader("user-agent", agent);

            HttpResponse response = httpclient.execute(httpget);

            return IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    private List<URI> getUrlsFromLines(Reader reader) throws IOException {
        List<URI> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(reader);

        list = br.lines()
                .filter(string -> string.startsWith(urlPrefix))
                .map(s -> {
                    try {
                        return new URI(s);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();

                        return null;
                    }
                })
                .collect(Collectors.toList());

        br.close();

        return list;
    }
}