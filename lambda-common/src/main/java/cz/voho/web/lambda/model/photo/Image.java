package cz.voho.web.lambda.model.photo;

import java.util.List;

public class Image {
    private BitmapDetails images;
    private List<String> tags;
    private String link;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public BitmapDetails getImages() {
        return images;
    }

    public void setImages(BitmapDetails images) {
        this.images = images;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
