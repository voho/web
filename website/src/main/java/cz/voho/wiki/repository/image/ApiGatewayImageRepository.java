package cz.voho.wiki.repository.image;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.voho.web.lambda.model.GenerateImageRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class ApiGatewayImageRepository implements WikiImageRepository {
    private static final Gson GSON = new GsonBuilder().create();
    private static final HttpClient HTTP_CLIENT = HttpClientBuilder.create().build();
    private static final String DOT_URL = "https://72sz610o1g.execute-api.eu-west-1.amazonaws.com/default/GraphVizLambda";
    private static final String PLANT_UML_URL = "https://o41mevzyj1.execute-api.eu-west-1.amazonaws.com/default/PlantUmlLambda";

    private static final RequestConfig REQUEST_CONFIG = RequestConfig
            .custom()
            .setCircularRedirectsAllowed(false)
            .setRelativeRedirectsAllowed(false)
            .setMaxRedirects(1)
            .setContentCompressionEnabled(true)
            .setConnectTimeout(3000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(5000)
            .build();

    @Override
    public byte[] generateDotSvg(final String source) throws Exception {
        final GenerateImageRequest request = new GenerateImageRequest();
        request.setFormat(GenerateImageRequest.FORMAT_SVG);
        request.setSource(source);
        return getImageUsingHttp(DOT_URL, request);
    }

    @Override
    public byte[] generatePlantUmlSvg(final String source) throws Exception {
        final GenerateImageRequest request = new GenerateImageRequest();
        request.setFormat(GenerateImageRequest.FORMAT_SVG);
        request.setSource(source);
        return getImageUsingHttp(PLANT_UML_URL, request);
    }

    private byte[] getImageUsingHttp(final String url, final GenerateImageRequest request) throws IOException {
        final String payload = GSON.toJson(request);
        final HttpPost post = new HttpPost(url);
        post.setConfig(REQUEST_CONFIG);
        post.setEntity(new BufferedHttpEntity(new StringEntity(payload, ContentType.APPLICATION_JSON)));
        HttpResponse response = HTTP_CLIENT.execute(post);
        if (response.getStatusLine().getStatusCode() == 200) {
            return ByteStreams.toByteArray(response.getEntity().getContent());
        }
        throw new IOException("Error fetching the entity: " + response.getStatusLine());
    }

    @Override
    public void warmUpCacheDotSvg(final String source) {
        // NOP
    }

    @Override
    public void warmUpCachePlantUmlSvg(final String source) {
        // NOP
    }
}
