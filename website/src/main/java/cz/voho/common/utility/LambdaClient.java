package cz.voho.common.utility;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.AWSLambdaException;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import cz.voho.web.lambda.model.GenerateImageRequest;
import cz.voho.web.lambda.model.GenerateImageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class LambdaClient {
    private static final Logger LOG = LoggerFactory.getLogger(LambdaClient.class);
    private static final int HTTP_OK = 200;
    private static final String PLANT_UML_LAMBDA = "PlantUmlLambda";
    private static final String GRAPH_VIZ_LAMBDA = "GraphVizLambda";

    private final AWSLambda lambda;

    public LambdaClient(final AWSLambda lambda) {
        this.lambda = lambda;
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
        lambdaRequest.setPayload(ApiUtility.toUglyJson(request));

        try {
            final InvokeResult lambdaResponse = lambda.invoke(lambdaRequest);

            if (lambdaResponse.getStatusCode() != HTTP_OK || !lambdaResponse.getPayload().hasRemaining()) {
                LOG.warn("Lambda image generating function completed with code {} ({}).", lambdaResponse.getStatusCode(), lambdaResponse.getFunctionError());
                return null;
            }

            final String payloadAsString = new String(lambdaResponse.getPayload().array(), StandardCharsets.UTF_8);
            return ApiUtility.fromJson(payloadAsString, responseType);
        } catch (AWSLambdaException e) {
            LOG.warn("Transient Lambda exception.");
            return null;
        }
    }
}
