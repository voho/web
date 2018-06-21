package cz.voho.web.lambda.model;

public class GenerateImageResponse {
    /**
     * text data (e.g. SVG)
     */
    private String textData;
    /**
     * binary data (e.g. PNG)
     */
    private byte[] binaryData;
    /**
     * output format: png, svg
     */
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(final String format) {
        this.format = format;
    }

    public String getTextData() {
        return textData;
    }

    public void setTextData(final String textData) {
        this.textData = textData;
    }

    public byte[] getBinaryData() {
        return binaryData;
    }

    public void setBinaryData(final byte[] binaryData) {
        this.binaryData = binaryData;
    }
}
