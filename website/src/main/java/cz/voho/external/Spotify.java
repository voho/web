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
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class Spotify {
    private static final String ARTIST_ID = "4OaWtKAgl8oGU9QTa4wXu4";
    public static final Base64.Encoder BASE64 = Base64.getEncoder();
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final HttpClient httpClient;
    private final String token;

    public Spotify(final HttpClient httpClient, final Configuration configuration) {
        this.httpClient = httpClient;
        this.token = configuration.getValue("SPOTIFY_TOKEN");
    }

    public List<SoundCloud.SoundCloudSong> getLatestSongs(final int limit) throws IOException {
        try {
            final JsonNode tokenJson = getTokenJsonNode();
            final String token = tokenJson.get("access_token").asText();

            final JsonNode albumsResponseTree = getAlbumsJsonNode(limit, token);
            albumsResponseTree.get("items").forEach(item -> {

            });
            System.out.println("albums2: " + albumsResponseTree);

            final SoundCloud.SoundCloudSong song = new SoundCloud.SoundCloudSong();
            song.setTitle("Weird Dream");
            song.setUrl("https://open.spotify.com/track/2BiIA55vSJE1sGZ1Zhwd9p?si=wJtjOnkYS9-Xkd0hsxn6gA");
            song.setWidgetUrl("https://open.spotify.com/embed/track/2BiIA55vSJE1sGZ1Zhwd9p");
            return Arrays.asList(song);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

    private JsonNode getAlbumsJsonNode(int limit, String token) throws IOException, URISyntaxException {
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("api.spotify.com")
                .setPath(String.format("/v1/artists/%s/albums", ARTIST_ID))
                .setParameter("offset", "0")
                .setParameter("limit", String.valueOf(limit))
                .setParameter("include_groups", "album,single,compilation,appears_on")
                .setParameter("market", "CZ")
                .build();

        final HttpGet request = new HttpGet(uri);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", "Bearer " + token);

        return executeAndParseResponse(request);
    }

    private JsonNode getTokenJsonNode() throws IOException, URISyntaxException {
        URI uri = new URIBuilder()
                .setScheme("https")
                .setHost("accounts.spotify.com")
                .setPath("/api/token")
                .build();

        final HttpPost request = new HttpPost(uri);
        request.setEntity(new UrlEncodedFormEntity(Collections.singletonList(new BasicNameValuePair("grant_type", "client_credentials"))));
        request.setHeader("Authorization", "Basic " + BASE64.encodeToString(token.getBytes(StandardCharsets.US_ASCII)));

        return executeAndParseResponse(request);
    }

    private JsonNode executeAndParseResponse(HttpUriRequest request) throws IOException {
        HttpResponse response = httpClient.execute(request);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("Invalid response code: " + response.getStatusLine().getStatusCode());
        }

        try (InputStream inputStream = response.getEntity().getContent()) {
            return OBJECT_MAPPER.readTree(inputStream);
        }
    }
}
