package ua.od.zakhariya.http.client.dowload.parser;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonParser {

    public VideoInfo getVideoInfo(String json) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(json);

        JSONObject videoModel = (JSONObject) parser.parse(jsonObject.get("videoModel").toString());
        String title = videoModel.get("title").toString();

        JSONObject settings = (JSONObject) parser.parse(jsonObject.get("xplayerSettings").toString());
        JSONObject sources = (JSONObject) parser.parse(settings.get("sources").toString());
        JSONObject hls = (JSONObject) parser.parse(sources.get("hls").toString());
        String url = hls.get("url").toString();
//        JSONArray mp4 = (JSONArray) sources.parse(hls.get("mp4").toString());

        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle(title);
        videoInfo.setPlaylistUri(url);

//        for (Object obj : mp4) {
//            String quality = ((JSONObject) obj).get("quality").toString();
//
//            if (quality.matches("^\\d*p$")) {
//                int qn = Integer.parseInt(quality.substring(0, quality.length() - 1));
//
//                if (qn > videoInfo.getQuality()) {
//                    videoInfo.setQuality(qn);
//                    videoInfo.setPlaylistUrl(((JSONObject) obj).get("url").toString());
//                }
//            }
//        }

        return videoInfo;
    }

    public class VideoInfo {
        private String title;
        private int quality;
        private String playlistUri;

        public String getTitle() {
            return title;
        }

        private void setTitle(String title) {
            this.title = title;
        }

        public int getQuality() {
            return quality;
        }

        private void setQuality(int quality) {
            this.quality = quality;
        }

        public String getPlaylistUri() {
            return playlistUri;
        }

        private void setPlaylistUri(String playlistUri) {
            this.playlistUri = playlistUri;
        }

        @Override
        public String toString() {
            return "VideoInfo{" +
                    "title='" + title + '\'' +
                    ", quality=" + quality +
                    ", playlistUrl='" + playlistUri + '\'' +
                    '}';
        }
    }
}