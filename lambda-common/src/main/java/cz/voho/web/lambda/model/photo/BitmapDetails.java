package cz.voho.web.lambda.model.photo;

public class BitmapDetails {
    private BitmapDetail thumbnail;
    private BitmapDetail low_resolution;
    private BitmapDetail standard_resolution;

    public BitmapDetail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(BitmapDetail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public BitmapDetail getLow_resolution() {
        return low_resolution;
    }

    public void setLow_resolution(BitmapDetail low_resolution) {
        this.low_resolution = low_resolution;
    }

    public BitmapDetail getStandard_resolution() {
        return standard_resolution;
    }

    public void setStandard_resolution(BitmapDetail standard_resolution) {
        this.standard_resolution = standard_resolution;
    }
}
