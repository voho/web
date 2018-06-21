package cz.voho.umlambda;

import cz.voho.web.lambda.model.GenerateImageFormat;
import cz.voho.web.lambda.model.GenerateImageRequest;
import cz.voho.web.lambda.model.GenerateImageResponse;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class HandlerTest {
    private static final String SIMPLE_VALID_SOURCE = "@startuml\nclass A {}\n@enduml";

    @Test
    public void sanityCheckSvg() throws Exception {
        final GenerateImageResponse response = process(SIMPLE_VALID_SOURCE, GenerateImageFormat.FORMAT_SVG);

        assertNotNull(response);
        assertNull(response.getBinaryData());
        assertNotNull(response.getTextData());
    }

    @Test
    public void sanityCheckPng() throws Exception {
        final GenerateImageResponse response = process(SIMPLE_VALID_SOURCE, GenerateImageFormat.FORMAT_PNG);

        assertNotNull(response);
        assertNull(response.getTextData());
        assertNotNull(response.getBinaryData());
    }

    private GenerateImageResponse process(final String source, final String format) {
        final Handler handler = new Handler();
        final GenerateImageRequest request = createTestRequest(source, format);
        final GenerateImageResponse response = handler.handleRequest(request, null);
        return response;
    }

    private GenerateImageRequest createTestRequest(final String source, final String format) {
        final GenerateImageRequest request = new GenerateImageRequest();
        request.setSource(source);
        request.setFormat(format);
        return request;
    }
}