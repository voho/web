package cz.voho.instalambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.voho.web.lambda.model.photo.GetRecentPhotosRequest;
import cz.voho.web.lambda.model.photo.GetRecentPhotosResponse;
import cz.voho.web.lambda.model.photo.Images;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The main AWS Lambda handler.
 */
public class Handler implements RequestHandler<GetRecentPhotosRequest, GetRecentPhotosResponse> {
    private static final Gson GSON = new GsonBuilder().create();
    private static final String INSTAGRAM_URI_FORMAT = "https://api.instagram.com/v1/users/self/media/recent?access_token=%s&count=%d";
    private static final String INSTAGRAM_TOKEN = System.getenv("INSTAGRAM_TOKEN");

    @Override
    public GetRecentPhotosResponse handleRequest(final GetRecentPhotosRequest request, final Context context) {
        try {
            final GetRecentPhotosResponse response = new GetRecentPhotosResponse();
            response.setRecentItems(fetch(request.getCount(), context));
            return response;
        } catch (Exception e) {
            log(context, "Internal error: %s", e.toString());
            throw new RuntimeException(e);
        }
    }

    private Images fetch(final int count, final Context context) throws IOException {
        try (CloseableHttpClient client = createHttpClient()) {
            final String url = generateUrl(count);
            final HttpResponse response = executeGetRequest(url, client);
            final String result = readResponse(response);
            return GSON.fromJson(result, Images.class);
        }
    }

    private String readResponse(final HttpResponse response) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8)) {
            return CharStreams.toString(reader);
        }
    }

    private HttpResponse executeGetRequest(final String url, final CloseableHttpClient client) throws IOException {
        return client.execute(new HttpGet(url));
    }

    private CloseableHttpClient createHttpClient() {
        return HttpClientBuilder.create().build();
    }

    private static String generateUrl(final int count) {
        return String.format(INSTAGRAM_URI_FORMAT, INSTAGRAM_TOKEN, count);
    }

    private void log(Context context, String messageFormat, Object... arguments) {
        if (context != null) {
            context.getLogger().log(String.format(messageFormat, arguments));
        }
    }
}
