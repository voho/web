package cz.voho.githublambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.io.CharStreams;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import cz.voho.web.lambda.model.github.CommitMeta;
import cz.voho.web.lambda.model.github.LatestCommitsRequest;
import cz.voho.web.lambda.model.github.LatestCommitsResponse;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
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
public class Handler implements RequestHandler<LatestCommitsRequest, LatestCommitsResponse> {
    private static final String GET_REPO_EVENTS_URI_FORMAT = "https://api.github.com/repos/voho/website/commits?path=%s";
    private static final String GITHUB_USER = System.getenv("GITHUB_USER");
    private static final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");
    private static final String GITHUB_AUTH = System.getenv("GITHUB_AUTH");

    @Override
    public LatestCommitsResponse handleRequest(final LatestCommitsRequest request, final Context context) {
        try {
            final LatestCommitsResponse response = new LatestCommitsResponse();
            response.setCommits(getPathCommits(context, request));
            return response;
        } catch (Exception e) {
            log(context, "Internal error: %s", e.toString());
            throw new RuntimeException(e);
        }
    }

    private List<CommitMeta> getPathCommits(Context context, LatestCommitsRequest request) throws IOException {
        List<CommitMeta> result = new LinkedList<>();
        String url = String.format(GET_REPO_EVENTS_URI_FORMAT, request.getPath());

        try (CloseableHttpClient client = createHttpClient()) {
            HttpResponse response = executeGetJsonRequest(context, url, client);
            String json = readResponse(context, response);
            ReadContext ctx = JsonPath.parse(json);
            List<String> urls = ctx.read("$[*].url");
            for (String u : urls) {
                HttpResponse commitResponse = executeGetJsonRequest(context, u, client);
                String json2 = readResponse(context, commitResponse);
                ReadContext ctx2 = JsonPath.parse(json2);
                CommitMeta cm = new CommitMeta();
                cm.setIsoTime(ctx2.read("$.commit.author.date"));
                cm.setMessage(ctx2.read("$.commit.message"));
                cm.setFilenames(ctx2.read("$.files[*].filename"));
                cm.setSha(ctx2.read("$.sha"));
                result.add(cm);
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
        HttpGet get = new HttpGet(url);
        get.setHeader("Accept", "application/json");
        get.setHeader("Authorization", GITHUB_AUTH);
        return client.execute(get);
    }

    private void validateResponseOk(Context context, HttpResponse response) throws IOException {
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            log(context, "HTTP status received: " + response.getStatusLine() + " USER: " + GITHUB_USER);
            throw new IOException("Invalid HTTP status: " + response.getStatusLine().getStatusCode());
        }
    }

    private CloseableHttpClient createHttpClient() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(GITHUB_USER, GITHUB_TOKEN);
        provider.setCredentials(AuthScope.ANY, credentials);
        return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
    }

    private void log(Context context, String messageFormat, Object... arguments) {
        if (context != null) {
            context.getLogger().log(String.format(messageFormat, arguments));
        }
    }
}
