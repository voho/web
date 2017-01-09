package cz.voho.web.lambda.model.generate;

public class GenerateImageRequest {
    /**
     * source data (in whatever syntax)
     */
    private String source;
    /**
     * output format: png, svg
     */
    private String format;

    public String getSource() {
        return source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }
}
