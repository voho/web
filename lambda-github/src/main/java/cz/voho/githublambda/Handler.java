package cz.voho.githublambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.io.CharStreams;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import cz.voho.web.lambda.model.github.CommitMeta;
import cz.voho.web.lambda.model.github.GetRecentCommitsRequest;
import cz.voho.web.lambda.model.github.GetRecentCommitsResponse;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
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

/**
 * The main AWS Lambda handler.
 */
public class Handler implements RequestHandler<GetRecentCommitsRequest, GetRecentCommitsResponse> {
    private static final String GET_REPO_EVENTS_URI_FORMAT = "https://api.github.com/repos/voho/website/commits?path=%s";
    private static final HttpHost TARGET_HOST = new HttpHost("api.github.com", 443, "https");
    private static final String GITHUB_USER = System.getenv("GITHUB_USER");
    private static final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");
    private static final UsernamePasswordCredentials CREDENTIALS = new UsernamePasswordCredentials(GITHUB_USER, GITHUB_TOKEN);

    @Override
    public GetRecentCommitsResponse handleRequest(final GetRecentCommitsRequest request, final Context context) {
        try {
            final GetRecentCommitsResponse response = new GetRecentCommitsResponse();
            response.setCommits(getPathCommits(context, request));
            return response;
        } catch (Exception e) {
            log(context, "Internal error: %s", e.toString());
            throw new RuntimeException(e);
        }
    }

    private List<CommitMeta> getPathCommits(Context context, GetRecentCommitsRequest request) throws IOException {
        List<CommitMeta> result = new LinkedList<>();
        String url = String.format(GET_REPO_EVENTS_URI_FORMAT, request.getPath());

        try (CloseableHttpClient client = createHttpClient()) {
            HttpResponse outerResponse = executeGetJsonRequest(context, url, client);
            String outerJson = readResponse(context, outerResponse);
            ReadContext outerContext = JsonPath.parse(outerJson);
            List<String> outerUrls = outerContext.read("$[*].url");

            for (String innerUrl : outerUrls) {
                HttpResponse innerResponse = executeGetJsonRequest(context, innerUrl, client);
                String innerJson = readResponse(context, innerResponse);
                ReadContext innerContext = JsonPath.parse(innerJson);

                CommitMeta commitMeta = new CommitMeta();
                commitMeta.setIsoTime(innerContext.read("$.commit.author.date"));
                commitMeta.setMessage(innerContext.read("$.commit.message"));
                commitMeta.setFilenames(innerContext.read("$.files[*].filename"));
                commitMeta.setSha(innerContext.read("$.sha"));
                result.add(commitMeta);
            }
        }

        return result;
    }

    private String readResponse(Context context, final HttpResponse response) throws IOException {
        validateResponseOk(context, response);
        try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        }
    }

    private HttpResponse executeGetJsonRequest(Context context, final String url, final CloseableHttpClient client) throws IOException {
        BasicScheme basicAuth = new BasicScheme();

        AuthCache authCache = new BasicAuthCache();
        authCache.put(TARGET_HOST, basicAuth);

        HttpClientContext clientContext = HttpClientContext.create();
        clientContext.setCredentialsProvider(createCredentialsProvider());
        clientContext.setAuthCache(authCache);

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");
        return client.execute(httpGet, clientContext);
    }

    private void validateResponseOk(Context context, HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IOException("Invalid HTTP status: " + response.getStatusLine().getStatusCode());
        }
    }

    private CredentialsProvider createCredentialsProvider() {
        CredentialsProvider result = new BasicCredentialsProvider();
        result.setCredentials(AuthScope.ANY, CREDENTIALS);
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

    private void log(Context context, String messageFormat, Object... arguments) {
        if (context != null) {
            context.getLogger().log(String.format(messageFormat, arguments));
        }
    }
}
