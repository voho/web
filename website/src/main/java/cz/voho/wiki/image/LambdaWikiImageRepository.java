package cz.voho.wiki.image;

import com.google.common.base.Strings;
import cz.voho.common.utility.LambdaClient;
import cz.voho.web.lambda.model.generate.GenerateImageFormat;
import cz.voho.web.lambda.model.generate.GenerateImageRequest;
import cz.voho.web.lambda.model.generate.GenerateImageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class LambdaWikiImageRepository implements WikiImageRepository {
    private static final Logger LOG = LoggerFactory.getLogger(LambdaWikiImageRepository.class);
    private final LambdaClient lambdaClient;

    public LambdaWikiImageRepository(final LambdaClient lambdaClient) {
        this.lambdaClient = lambdaClient;
    }

    @Override
    public byte[] generateDotSvg(final String source) throws Exception {
        final GenerateImageRequest request = new GenerateImageRequest();
        request.setFormat(GenerateImageFormat.FORMAT_SVG);
        request.setSource(source);
        final GenerateImageResponse response = lambdaClient.callDotLambda(request);
        if (response == null || Strings.isNullOrEmpty(response.getTextData())) {
            LOG.warn("No response for DOT image: {}", source);
            return null;
        }
        return response.getTextData().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public byte[] generatePlantUmlSvg(final String source) throws Exception {
        final GenerateImageRequest request = new GenerateImageRequest();
        request.setFormat(GenerateImageFormat.FORMAT_SVG);
        request.setSource(source);
        final GenerateImageResponse response = lambdaClient.callPlantUmlLambda(request);
        if (response == null || Strings.isNullOrEmpty(response.getTextData())) {
            LOG.warn("No response for Plant UML image: {}", source);
            return null;
        }
        return response.getTextData().getBytes(StandardCharsets.UTF_8);
    }
}
