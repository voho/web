package cz.voho.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class Spotify {
    private static final String ARTIST_ID = "4OaWtKAgl8oGU9QTa4wXu4";
    private static final Base64.Encoder BASE64 = Base64.getEncoder();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final HttpClient httpClient;
    private final String token;

    public Spotify(final HttpClient httpClient, final Configuration configuration) {
        this.httpClient = httpClient;
        this.token = configuration.getValue("SPOTIFY_TOKEN");
    }

    public List<SpotifySong> getLatestSongs(final int limit) throws IOException {
        try {
            final JsonNode tokenJson = getTokenJsonNode();
            final String token = tokenJson.get("access_token").asText();
            final JsonNode albumsResponseTree = getAlbumsJsonNode(limit, token);

            final List<SpotifySong> songs = new ArrayList<>(limit);

            albumsResponseTree.get("items").forEach(item -> {
                final String trackTitle = item.get("name").asText();
                final String trackUri = item.get("uri").asText();
                final String trackUrl = item.get("external_urls").get("spotify").asText();

                final SpotifySong song = new SpotifySong();
                song.setTitle(trackTitle);
                song.setUrl(trackUrl);
                song.setWidgetUrl("https://embed.spotify.com/?uri=" + trackUri);
                songs.add(song);
            });

            return songs;
        } catch (final URISyntaxException e) {
            throw new IOException(e);
        }
    }

    private JsonNode getAlbumsJsonNode(final int limit, final String token) throws IOException, URISyntaxException {
        final URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.spotify.com")
                .setPath(String.format("/v1/artists/%s/albums", ARTIST_ID))
                .setParameter("limit", String.valueOf(limit))
                .build();

        final HttpGet request = new HttpGet(uri);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer " + token);

        return executeAndParseResponse(request);
    }

    private JsonNode getTokenJsonNode() throws IOException, URISyntaxException {
        final URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("accounts.spotify.com")
                .setPath("/api/token")
                .build();

        final HttpPost request = new HttpPost(uri);
        request.setEntity(new UrlEncodedFormEntity(Collections.singletonList(new BasicNameValuePair("grant_type", "client_credentials"))));
        request.setHeader("Authorization", "Basic " + BASE64.encodeToString(token.getBytes(StandardCharsets.US_ASCII)));

        return executeAndParseResponse(request);
    }

    private JsonNode executeAndParseResponse(final HttpUriRequest request) throws IOException {
        final HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("Invalid response code: " + response.getStatusLine().getStatusCode());
        }

        try (final InputStream inputStream = response.getEntity().getContent()) {
            return OBJECT_MAPPER.readTree(inputStream);
        }
    }

    public static class SpotifySong {
        private String title;
        private String url;
        private String widgetUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(final String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(final String url) {
            this.url = url;
        }

        public String getWidgetUrl() {
            return widgetUrl;
        }

        public void setWidgetUrl(final String widgetUrl) {
            this.widgetUrl = widgetUrl;
        }
    }
}
