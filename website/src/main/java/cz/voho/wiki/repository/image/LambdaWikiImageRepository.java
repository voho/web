package cz.voho.wiki.repository.image;

import cz.voho.common.utility.LambdaClient;
import cz.voho.web.lambda.model.GenerateImageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaWikiImageRepository implements WikiImageRepository {
    private static final Logger LOG = LoggerFactory.getLogger(LambdaWikiImageRepository.class);

    private final LambdaClient lambdaClient;

    public LambdaWikiImageRepository(final LambdaClient lambdaClient) {
        this.lambdaClient = lambdaClient;
    }

    @Override
    public byte[] generateDotSvg(final String source) throws Exception {
        final GenerateImageRequest request = new GenerateImageRequest();
        request.setFormat(GenerateImageRequest.FORMAT_SVG);
        request.setSource(source);

        LOG.info("Calling DOT lambda...");
        return lambdaClient.callDotLambda(request);
    }

    @Override
    public byte[] generatePlantUmlSvg(final String source) throws Exception {
        final GenerateImageRequest request = new GenerateImageRequest();
        request.setFormat(GenerateImageRequest.FORMAT_SVG);
        request.setSource(source);

        LOG.info("Calling Plant UML lambda...");
        return lambdaClient.callPlantUmlLambda(request);
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
