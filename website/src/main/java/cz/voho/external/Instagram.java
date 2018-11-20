package cz.voho.external;

import com.google.common.io.CharStreams;
import cz.voho.common.utility.ApiUtility;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Instagram {
    // TODO can also use https://rsshub.app/instagram/user/vohocz later
    private static final String INSTAGRAM_URI_FORMAT = "https://api.instagram.com/v1/users/self/media/recent?access_token=%s&count=%d";

    private final HttpClient httpClient;
    private final String token;

    public Instagram(final HttpClient httpClient, final Configuration configuration) {
        this.httpClient = httpClient;
        this.token = configuration.getValue("INSTAGRAM_TOKEN");
    }

    public List<InstagramImage> getLatestPhotos(final int count) throws IOException {
        final String url = generateUrl(count);
        final HttpResponse response = executeGetRequest(url, httpClient);
        final String result = readResponse(response);
        InstagramImages images = ApiUtility.fromJson(result, InstagramImages.class);
        return images.getData();
    }

    private String readResponse(final HttpResponse response) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        }
    }

    private HttpResponse executeGetRequest(final String url, final HttpClient client) throws IOException {
        return client.execute(new HttpGet(url));
    }

    private String generateUrl(final int count) {
        return String.format(INSTAGRAM_URI_FORMAT, token, count);
    }

    public static class InstagramBitmapDetail {
        private int width;
        private int height;
        private String url;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class InstagramBitmapDetails {
        private InstagramBitmapDetail thumbnail;
        private InstagramBitmapDetail low_resolution;
        private InstagramBitmapDetail standard_resolution;

        public InstagramBitmapDetail getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(InstagramBitmapDetail thumbnail) {
            this.thumbnail = thumbnail;
        }

        public InstagramBitmapDetail getLow_resolution() {
            return low_resolution;
        }

        public void setLow_resolution(InstagramBitmapDetail low_resolution) {
            this.low_resolution = low_resolution;
        }

        public InstagramBitmapDetail getStandard_resolution() {
            return standard_resolution;
        }

        public void setStandard_resolution(InstagramBitmapDetail standard_resolution) {
            this.standard_resolution = standard_resolution;
        }
    }

    public static class InstagramImage {
        private InstagramBitmapDetails images;
        private List<String> tags;
        private String link;

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public InstagramBitmapDetails getImages() {
            return images;
        }

        public void setImages(InstagramBitmapDetails images) {
            this.images = images;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    public static class InstagramImages {
        private List<InstagramImage> data;

        public List<InstagramImage> getData() {
            return data;
        }

        public void setData(List<InstagramImage> data) {
            this.data = data;
        }
    }
}
