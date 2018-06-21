package cz.voho.external;

import com.google.common.io.CharStreams;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class GitHub {
    private static final String GET_REPO_EVENTS_URI_FORMAT = "https://api.github.com/repos/voho/website/commits?path=%s";
    private static final HttpHost TARGET_HOST = new HttpHost("api.github.com", 443, "https");

    private final HttpClient httpClient;
    private final UsernamePasswordCredentials credentials;

    public GitHub(final HttpClient httpClient, final Configuration configuration) {
        String githubUser = configuration.getValue("GITHUB_USER");
        String githubToken = configuration.getValue("GITHUB_TOKEN");
        this.httpClient = httpClient;
        this.credentials = new UsernamePasswordCredentials(githubUser, githubToken);
    }

    public List<GitHubCommitMeta> getLatestCommits(String path) throws IOException {
        List<GitHubCommitMeta> result = new LinkedList<>();
        String url = String.format(GET_REPO_EVENTS_URI_FORMAT, path);

        HttpResponse outerResponse = executeGetJsonRequest(url);
        String outerJson = readResponse(outerResponse);
        ReadContext outerContext = JsonPath.parse(outerJson);
        List<String> outerUrls = outerContext.read("$[*].url");

        for (String innerUrl : outerUrls) {
            HttpResponse innerResponse = executeGetJsonRequest(innerUrl);
            String innerJson = readResponse(innerResponse);
            ReadContext innerContext = JsonPath.parse(innerJson);

            GitHubCommitMeta commitMeta = new GitHubCommitMeta();
            commitMeta.setIsoTime(innerContext.read("$.commit.author.date"));
            commitMeta.setMessage(innerContext.read("$.commit.message"));
            commitMeta.setFilenames(innerContext.read("$.files[*].filename"));
            commitMeta.setSha(innerContext.read("$.sha"));
            result.add(commitMeta);
        }

        return result;
    }

    private String readResponse(final HttpResponse response) throws IOException {
        validateResponseOk(response);
        try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        }
    }

    private HttpResponse executeGetJsonRequest(final String url) throws IOException {
        BasicScheme basicAuth = new BasicScheme();

        AuthCache authCache = new BasicAuthCache();
        authCache.put(TARGET_HOST, basicAuth);

        HttpClientContext clientContext = HttpClientContext.create();
        clientContext.setCredentialsProvider(createCredentialsProvider());
        clientContext.setAuthCache(authCache);

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        return httpClient.execute(httpGet, clientContext);
    }

    private void validateResponseOk(HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException("Invalid HTTP status: " + response.getStatusLine().getStatusCode());
        }
    }

    private CredentialsProvider createCredentialsProvider() {
        CredentialsProvider result = new BasicCredentialsProvider();
        result.setCredentials(AuthScope.ANY, credentials);
        return result;
    }

    private CloseableHttpClient createHttpClient() {
        return HttpClientBuilder
                .create()
                .setDefaultRequestConfig(
                        RequestConfig
                                .custom()
                                .setRedirectsEnabled(true)
                                .setAuthenticationEnabled(true)
                                .build()
                )
                .build();
    }

    public static class GitHubCommitMeta {
        // read. LocalDateTime.parse(isoTime, DateTimeFormatter.ISO_DATE_TIME);
        private String isoTime;
        private String sha;
        private String message;
        private List<String> filenames;

        public List<String> getFilenames() {
            return filenames;
        }

        public void setFilenames(List<String> filenames) {
            this.filenames = filenames;
        }

        public String getIsoTime() {
            return isoTime;
        }

        public void setIsoTime(String isoTime) {
            this.isoTime = isoTime;
        }

        public String getSha() {
            return sha;
        }

        public void setSha(String sha) {
            this.sha = sha;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
