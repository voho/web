package cz.voho.common.utility;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.AWSLambdaException;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cz.voho.web.lambda.model.GenerateImageRequest;
import cz.voho.web.lambda.model.GenerateImageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class LambdaClient {
    private static final Logger LOG = LoggerFactory.getLogger(LambdaClient.class);
    private static final Gson GSON = new GsonBuilder().create();
    private static final int HTTP_OK = 200;
    private static final String PLANT_UML_LAMBDA = "PlantUmlLambda";
    private static final String GRAPH_VIZ_LAMBDA = "GraphVizLambda";
    private static final String AWS_KEY = System.getProperty("AWS_KEY");
    private static final String AWS_SECRET = System.getProperty("AWS_SECRET");

    private final AWSLambda lambda;

    public LambdaClient() {
        this(createDefaultLambdaClient());
    }

    public LambdaClient(final AWSLambda lambda) {
        this.lambda = lambda;
    }

    private static AWSLambda createDefaultLambdaClient() {
        return AWSLambdaClientBuilder
                .standard()
                .withCredentials(credentials())
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    private static AWSCredentialsProvider credentials() {
        if (AWS_KEY == null || AWS_SECRET == null) {
            return new DefaultAWSCredentialsProviderChain();
        } else {
            return new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_KEY, AWS_SECRET));
        }
    }

    public GenerateImageResponse callPlantUmlLambda(final GenerateImageRequest request) {
        return call(PLANT_UML_LAMBDA, request, GenerateImageResponse.class);
    }

    public GenerateImageResponse callDotLambda(final GenerateImageRequest request) {
        return call(GRAPH_VIZ_LAMBDA, request, GenerateImageResponse.class);
    }

    private <I, O> O call(final String lambdaFunctionName, final I request, final Class<O> responseType) {
        final InvokeRequest lambdaRequest = new InvokeRequest();
        lambdaRequest.setFunctionName(lambdaFunctionName);
        lambdaRequest.setPayload(GSON.toJson(request));

        try {
            final InvokeResult lambdaResponse = lambda.invoke(lambdaRequest);

            if (lambdaResponse.getStatusCode() != HTTP_OK || !lambdaResponse.getPayload().hasRemaining()) {
                LOG.warn("Lambda image generating function completed with code {} ({}).", lambdaResponse.getStatusCode(), lambdaResponse.getFunctionError());
                return null;
            }

            final String payloadAsString = new String(lambdaResponse.getPayload().array(), StandardCharsets.UTF_8);
            return GSON.fromJson(payloadAsString, responseType);
        } catch (AWSLambdaException e) {
            LOG.warn("Transient Lambda exception.");
            return null;
        }
    }
}
